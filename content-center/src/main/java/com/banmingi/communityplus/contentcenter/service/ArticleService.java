package com.banmingi.communityplus.contentcenter.service;

import com.alibaba.fastjson.JSON;
import com.banmingi.communityplus.contentcenter.dto.ArticleAuditDTO;
import com.banmingi.communityplus.contentcenter.dto.ArticleListDTO;
import com.banmingi.communityplus.contentcenter.dto.ArticlePublishDTO;
import com.banmingi.communityplus.contentcenter.dto.UserAddBonusMsgDTO;
import com.banmingi.communityplus.contentcenter.entity.Article;
import com.banmingi.communityplus.contentcenter.entity.RocketMQTransactionLog;
import com.banmingi.communityplus.contentcenter.enums.ArticleAddBonusValueEnum;
import com.banmingi.communityplus.contentcenter.enums.ArticleSortEnum;
import com.banmingi.communityplus.contentcenter.enums.ArticleStatusEnum;
import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import com.banmingi.communityplus.contentcenter.mapper.RocketMQTransactionLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final RedisTemplate<String,Object> redisTemplate;
    private final Source source;

    private static final String ARTICLE_SAVE_KEY = "article:save:";

    /**
     * 发布或编辑文章
     * @param articlePublishDTO
     */
    public void publishOrUpdate(ArticlePublishDTO articlePublishDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articlePublishDTO,article);
        if (article.getId() == null){
            article.setAuditStatus(ArticleStatusEnum.NOT_YET.getStatus());
            article.setCreateTime(System.currentTimeMillis());
            article.setModifyTime(System.currentTimeMillis());
            this.articleMapper.insert(article);
        } else {
            article.setModifyTime(System.currentTimeMillis());
            this.articleMapper.updateById(article);
        }
        //移除redis中保存的文章
        this.redisTemplate.delete(ARTICLE_SAVE_KEY + articlePublishDTO.getUserId());
    }

    /**
     * 保存文章(暂时保存7天)
     * @param articlePublishDTO
     */
    public void save(ArticlePublishDTO articlePublishDTO) {
        Integer userId = articlePublishDTO.getUserId();
        this.redisTemplate.opsForValue().set(ARTICLE_SAVE_KEY+userId,articlePublishDTO,7L, TimeUnit.DAYS);
    }

    /**
     * 获取保存的文章
     * @return
     */
   public ArticlePublishDTO getTheSavedArticle(Integer userId) {
       Boolean hasArticle = this.redisTemplate.hasKey(ARTICLE_SAVE_KEY + userId);
       if (!hasArticle) {
           return null;
       }
       return (ArticlePublishDTO) this.redisTemplate.opsForValue().get(ARTICLE_SAVE_KEY + userId);
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
        if (!article.getAuditStatus().equals(ArticleStatusEnum.NOT_YET.getStatus())) {
            throw new IllegalArgumentException("参数非法!该分享已审核通过或未通过!");
        }

        //2. 如果是PASS,那么发送消息给rockerMQ,让用户中心去消费,为发布人添加积分
        if (articleAuditDTO.getArticleStatus().equals(ArticleStatusEnum.PASS.getStatus())) {
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
            //推送消息给rocketmq,为用户添加积分
            this.source.output().send(message);

            //把文章添加
        } else {
            //如果是REJECT(审核不通过),直接更新数据库中文章的状态即可
            this.auditInDB(id,articleAuditDTO);
        }
    }

    /**
     *审核文章,将状态设置为 PASS/REJECT
     * @param id
     * @param articleAuditDTO
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
     * @param id
     * @param articleAuditDTO
     * @param transactionId
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
     * @param pageNo 页号
     * @param pageSize 一页的文章条数
     * @return 文章列表
     */
    public PageInfo<ArticleListDTO> q(String search, Integer categoryId, String sort, Integer pageNo, Integer pageSize) {
        //分页
        //切入下面不分页的SQL,自动拼接分页的SQL
        PageHelper.startPage(pageNo,pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        //搜寻条件不为空
        if (StringUtils.isNotBlank(search)) {
            wrapper.like("title",search);
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
                    wrapper.orderByDesc("modify_time");
                }
                //最热文章排序(根据浏览数)
                if (articleSortEnum == ArticleSortEnum.HOT) {
                    wrapper.orderByDesc("view_count","modify_time");
                }
                //根据点赞数排序
                if (articleSortEnum == ArticleSortEnum.COMMENT) {
                    wrapper.orderByDesc("like_count","modify_time");
                }
                //根据评论数排序
                if (articleSortEnum == ArticleSortEnum.COMMENT) {
                    wrapper.orderByDesc("comment_count","modify_time");
                }
                //根据收藏数排序
                if (articleSortEnum == ArticleSortEnum.COLLECTION) {
                    wrapper.orderByDesc("collection_count","modify_time");
                }
                break;
            }
        }
        //根据条件查询结果
        List<Article> articleList = this.articleMapper.selectList(wrapper);

        //构建返回结果

        List<ArticleListDTO> articleListDTOList = articleList.stream().map(article -> {
            ArticleListDTO articleListDTO = new ArticleListDTO();
            BeanUtils.copyProperties(article, articleListDTO);
            return articleListDTO;
        }).collect(Collectors.toList());


        return new  PageInfo<ArticleListDTO>(articleListDTOList);
    }
}
