package com.banmingi.communityplus.contentcenter.service;

import com.alibaba.fastjson.JSON;
import com.banmingi.communityplus.contentcenter.dto.article.ArticleAuditDTO;
import com.banmingi.communityplus.contentcenter.dto.article.ArticleDTO;
import com.banmingi.communityplus.contentcenter.dto.article.ArticleListDTO;
import com.banmingi.communityplus.contentcenter.dto.article.ArticlePublishDTO;
import com.banmingi.communityplus.contentcenter.dto.user.UserAddBonusMsgDTO;
import com.banmingi.communityplus.contentcenter.dto.user.UserDTO;
import com.banmingi.communityplus.contentcenter.entity.Article;
import com.banmingi.communityplus.contentcenter.entity.Category;
import com.banmingi.communityplus.contentcenter.entity.RocketMQTransactionLog;
import com.banmingi.communityplus.contentcenter.enums.ArticleAddBonusValueEnum;
import com.banmingi.communityplus.contentcenter.enums.ArticleSortEnum;
import com.banmingi.communityplus.contentcenter.enums.ArticleStatusEnum;
import com.banmingi.communityplus.contentcenter.feignclient.UserFeignClient;
import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import com.banmingi.communityplus.contentcenter.mapper.CategoryMapper;
import com.banmingi.communityplus.contentcenter.mapper.RocketMQTransactionLogMapper;
import com.banmingi.communityplus.contentcenter.rocketmq.output.AddBonusSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final RocketMQTransactionLogMapper rocketMQTransactionLogMapper;
    private final CategoryMapper categoryMapper;
    private final RedisTemplate<String,Object> redisTemplate;
    private final UserFeignClient userFeignClient;
    private final AddBonusSource addBonusSource;

    private static final String ARTICLE_SAVE_KEY = "article:save:";
    private static final String ARTICLE_ID_KEY = "article:id:";


    /**
     * 发布或编辑文章
     * @param articlePublishDTO 文章发布实体
     */
    public void publishOrUpdate(ArticlePublishDTO articlePublishDTO) {
        //移除redis中保存的文章
        this.redisTemplate.delete(ARTICLE_SAVE_KEY + articlePublishDTO.getUserId());

        Article article = new Article();
        BeanUtils.copyProperties(articlePublishDTO,article);
        if (article.getId() == null){
            article.setAuditStatus(ArticleStatusEnum.NOT_YET.name());
            article.setCreateTime(System.currentTimeMillis());
            article.setModifyTime(System.currentTimeMillis());
            this.articleMapper.insert(article);
        } else {
            //移除redis中保存的文章详情
            this.redisTemplate.delete(ARTICLE_ID_KEY + article.getId());
            //更新数据库
            article.setModifyTime(System.currentTimeMillis());
            this.articleMapper.updateById(article);

        }
    }

    /**
     * 保存文章(暂时保存7天)
     * @param articlePublishDTO ArticlePublishDTO
     */
    public void save(ArticlePublishDTO articlePublishDTO) {
        Integer userId = articlePublishDTO.getUserId();
        this.redisTemplate.opsForValue().set(ARTICLE_SAVE_KEY+userId,articlePublishDTO,7L, TimeUnit.DAYS);
    }

    /**
     * 获取保存的文章
     * @return ArticlePublishDTO
     */
   public ArticlePublishDTO getTheSavedArticle(Integer userId) {
       Boolean hasArticle = this.redisTemplate.hasKey(ARTICLE_SAVE_KEY + userId);
       if (Objects.equals(hasArticle,false)) {
           return null;
       }
       return (ArticlePublishDTO) this.redisTemplate.opsForValue().get(ARTICLE_SAVE_KEY + userId);
   }

    /**
     * 获取需要编辑的文章
     * @param id id
     * @return ArticlePublishDTO
     */
    public ArticlePublishDTO getEditArticle(Integer id) {
        ArticlePublishDTO articlePublishDTO = new ArticlePublishDTO();
        Article article = this.articleMapper.selectById(id);
        if (article != null) {
            BeanUtils.copyProperties(article,articlePublishDTO);
            return articlePublishDTO;
        }
        return null;
    }


    /**
     * 审核文章 并为用户添加积分
     * @param id 文章id
     * @param articleAuditDTO 审核状态
     */
    public void audit(Integer id, ArticleAuditDTO articleAuditDTO) {
        //1. 查询 article 是否存在,不存在或者当前的audit_status != NOT_YET,那么就抛异常
        Article article = this.articleMapper.selectById(id);
        if (article == null) {
            throw new IllegalArgumentException("参数非法！该文章不存在");
        }
        if (!article.getAuditStatus().equals(ArticleStatusEnum.NOT_YET.name())) {
            throw new IllegalArgumentException("参数非法!该文章已审核通过或未通过!");
        }

        //2. 如果是PASS,那么发送消息给rockerMQ,让用户中心去消费,为发布人添加积分
        if (articleAuditDTO.getArticleStatus().equals(ArticleStatusEnum.PASS.name())) {
            //生成事务id
            String transactionId = UUID.randomUUID().toString();
            //构建消息体
            Message<UserAddBonusMsgDTO> message = MessageBuilder.withPayload(
                    UserAddBonusMsgDTO.builder()
                            .userId(article.getUserId())
                            .bonus(ArticleAddBonusValueEnum.CREATED.getValue())
                            .event(ArticleAddBonusValueEnum.CREATED.getEvent())
                            .description(ArticleAddBonusValueEnum.CREATED.getDescription())
                            .build())
                    //消息头
                    .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                    .setHeader("articleId", id)
                    //header中传的对象,在get的时候拿到的是字符串,所以我们把对象转换成Json字符串
                    .setHeader("articleAuditDTO", JSON.toJSONString(articleAuditDTO))
                    .build();
            //推送消息给rocketMq,为用户添加积分
            this.addBonusSource.addBonusOutput().send(message);

            //把文章添加
        } else {
            //如果是REJECT(审核不通过),直接更新数据库中文章的状态即可
            this.auditInDB(id,articleAuditDTO);
        }
    }

    /**
     *审核文章,将状态设置为 PASS/REJECT
     * @param id 文章id
     * @param articleAuditDTO 审核状态dto
     */
    public void auditInDB(Integer id,ArticleAuditDTO articleAuditDTO) {
        Article article = Article.builder()
                .id(id)
                .auditStatus(articleAuditDTO.getArticleStatus())
                .reason(articleAuditDTO.getReason())
                .build();
        this.articleMapper.updateById(article);
    }

    /**
     * 审核资源,将状态设为PASS/REJECT,并记录事务日志
     * @param id 文章id
     * @param articleAuditDTO 审核状态dto
     * @param transactionId 本地事务id
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditInDBWithRocketMQLog(Integer id,
                                         ArticleAuditDTO articleAuditDTO,
                                         String transactionId) {
       this.auditInDB(id,articleAuditDTO);
        this.rocketMQTransactionLogMapper.insert(
                RocketMQTransactionLog.builder()
                .transactionId(transactionId)
                .log("审核文章,并为发布者添加积分...")
                .build());
    }

    /**
     *
     * @param search 搜索条件
     * @param categoryId 分类
     * @param sort 排序方式
     * @param pageNum 页号
     * @param pageSize 一页的文章条数
     * @return 文章列表
     */
    public PageInfo<ArticleListDTO> q(String search, Integer categoryId, String sort, Integer pageNum, Integer pageSize) {
        //构建查询条件
        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        //文章审核通过且公开
        wrapper.eq("audit_status",ArticleStatusEnum.PASS.name());
        wrapper.eq("show_flag",1);
        //搜寻条件不为空
        if (StringUtils.isNotBlank(search)) {
            wrapper.like("title",search).or().like("tag",search);
        }
        //分类查询不为空
        if (categoryId != null) {
            wrapper.eq("category_id",categoryId);
        }
        //排序方式
        for (ArticleSortEnum articleSortEnum : ArticleSortEnum.values()) {
            if (articleSortEnum.name().toLowerCase().equals(sort)) {
                //最新文章排序
                if (articleSortEnum == ArticleSortEnum.NEW) {
                    wrapper.orderByDesc("create_time");
                }
                //最热文章排序(根据浏览数)
                if (articleSortEnum == ArticleSortEnum.HOT) {
                    wrapper.orderByDesc("view_count","create_time");
                }
                //根据点赞数排序
                if (articleSortEnum == ArticleSortEnum.LIKE) {
                    wrapper.orderByDesc("like_count","create_time");
                }
                //根据评论数排序
                if (articleSortEnum == ArticleSortEnum.COMMENT) {
                    wrapper.orderByDesc("comment_count","create_time");
                }
                //根据收藏数排序
                if (articleSortEnum == ArticleSortEnum.COLLECTION) {
                    wrapper.orderByDesc("collection_count","create_time");
                }
                break;
            }
        }
        //分页
        //切入下面不分页的SQL,自动拼接分页的SQL
        PageHelper.startPage(pageNum,pageSize);
        //根据条件查询结果
        List<Article> articleList = this.articleMapper.selectList(wrapper);
        /*
          因为PageHelper针对的分页是切入数据库的物理分页,这里只能对Article进行分页,但想要的返回结果是ArticleListDTO，
          所以现在这里获取到PageInfo<Article>的分页属性,然后再在下面从新赋值给PageInfo<ArticleListDTO>
         */
        PageInfo<Article> articlePageInfo = new PageInfo<>(articleList);

        //构建返回结果
        List<ArticleListDTO> articleListDTOList = articleList.stream().map(article -> {
            ArticleListDTO articleListDTO = new ArticleListDTO();
            BeanUtils.copyProperties(article, articleListDTO);
            //用户头像
            String avatarUrl = this.userFeignClient.findById(articleListDTO.getUserId()).getAvatarUrl();
            articleListDTO.setAvatarUrl(avatarUrl);
            //分类图片
            String categoryImage = this.categoryMapper.selectById(articleListDTO.getCategoryId()).getImage();
            articleListDTO.setCategoryImage(categoryImage);
            return articleListDTO;
        }).collect(Collectors.toList());

        //PageInfo<Article> --> PageInfo<ArticleListDTO>
        PageInfo<ArticleListDTO> articleListDTOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(articlePageInfo,articleListDTOPageInfo);
        articleListDTOPageInfo.setList(articleListDTOList);

        return articleListDTOPageInfo;
    }



    /**
     * 文件文章id查找文章
     * @param id 文章id
     * @return 文章详情实体
     */
    public ArticleDTO findById(Integer id) {

        //先从缓存中查询文字详情
        ArticleDTO articleDTO = (ArticleDTO) this.redisTemplate.opsForValue().get(ARTICLE_ID_KEY + id);
        //缓存中有的话
        if (articleDTO != null) {
            //更新缓存文章阅读数 +1
            articleDTO.setViewCount(articleDTO.getViewCount() + 1);
            this.redisTemplate.opsForValue().set(ARTICLE_ID_KEY+id,articleDTO,7L,TimeUnit.DAYS);
            return articleDTO;
        }

        //从数据库中查询
        Article article = this.articleMapper.selectById(id);
        //构建文章详情实体
        ArticleDTO articleDTOCache = new ArticleDTO();
        //文章不为null && 文章审核通过 && 文章公开
        if (article!=null && Objects.equals(article.getAuditStatus(),ArticleStatusEnum.PASS.name()) && article.getShowFlag().equals(1)) {

            //更新数据库阅读数 +1
            article.setViewCount(article.getViewCount() + 1);
            this.articleMapper.updateById(article);

            //构建响应
            BeanUtils.copyProperties(article,articleDTOCache);
            //查询作者信息
            UserDTO userDTO = this.userFeignClient.findById(article.getUserId());
            //查询分类信息
            Category category = this.categoryMapper.selectById(article.getCategoryId());
            articleDTOCache.setUserDTO(userDTO);
            articleDTOCache.setCategory(category);
            //如果文章浏览数超过10000的话,就将文章详情放入缓存
            if (articleDTOCache.getViewCount()>=1000) {
                this.redisTemplate.opsForValue().set(ARTICLE_ID_KEY+id,articleDTOCache,7L,TimeUnit.DAYS);
            }
        }
        return articleDTOCache;
    }

    /**
     * 删除文章
     * @param id 文章id
     */
    public void deleteById(Integer id) {
        this.redisTemplate.delete(ARTICLE_ID_KEY + id);
        this.articleMapper.deleteById(id);
    }


}
