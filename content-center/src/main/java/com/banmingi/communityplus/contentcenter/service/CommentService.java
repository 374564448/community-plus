package com.banmingi.communityplus.contentcenter.service;

import com.banmingi.communityplus.commons.enums.NotificationTypeEnum;
import com.banmingi.communityplus.contentcenter.dto.article.ArticleDTO;
import com.banmingi.communityplus.contentcenter.dto.comment.CommentCreateDTO;
import com.banmingi.communityplus.contentcenter.dto.comment.CommentDTO;
import com.banmingi.communityplus.contentcenter.dto.notification.NotificationCreateDTO;
import com.banmingi.communityplus.contentcenter.dto.user.UserDTO;
import com.banmingi.communityplus.contentcenter.entity.Article;
import com.banmingi.communityplus.contentcenter.entity.Comment;
import com.banmingi.communityplus.contentcenter.enums.CommentTypeEnum;
import com.banmingi.communityplus.contentcenter.feignclient.UserFeignClient;
import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import com.banmingi.communityplus.contentcenter.mapper.CommentMapper;
import com.banmingi.communityplus.contentcenter.rocketmq.output.NotificationSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 半命i 2020/6/11
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {

    private final CommentMapper commentMapper;
    private final ArticleMapper articleMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserFeignClient userFeignClient;
    private final NotificationSource notificationSource;

    private static final String ARTICLE_ID_KEY = "article:id:";

    /**
     * 评论
     *
     * @param commentCreateDTO 实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void comment(CommentCreateDTO commentCreateDTO) {
        //构建评论实体
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        comment.setCreateTime(System.currentTimeMillis());

        //评论插入数据库
        this.commentMapper.insert(comment);

        //如果是二级评论的话,针对这条评论的评论数要 +1
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //更新数据库
            // 更新评论列表的回复数
            Comment listComment = this.commentMapper.selectById(comment.getCommentListId());
            listComment.setCommentCount(listComment.getCommentCount() + 1);
            this.commentMapper.updateById(listComment);

            //更新父评论的回复数
            if (!comment.getCommentListId().equals(comment.getParentId())) {
                Comment parentComment = this.commentMapper.selectById(comment.getParentId());
                parentComment.setCommentCount(parentComment.getCommentCount() + 1);
                this.commentMapper.updateById(parentComment);
            }
        }
        //文章评论数显示的是总的评论数,所以要 +1
        //看缓存中是否有文章
        ArticleDTO articleDTO = getArticleFromCache(comment.getArticleId());
        if (articleDTO != null) {
            //更新缓存文章评论数 +1
            articleDTO.setCommentCount(articleDTO.getCommentCount() + 1);
            this.redisTemplate.opsForValue().set(ARTICLE_ID_KEY + comment.getArticleId(), articleDTO, 7L, TimeUnit.DAYS);
            //更新数据库
            Article article = new Article();
            BeanUtils.copyProperties(articleDTO, article);
            this.articleMapper.updateById(article);
        } else {
            //缓存中没有文章的话,直接更新数据库
            Article article = this.articleMapper.selectById(comment.getArticleId());
            article.setCommentCount(article.getCommentCount() + 1);
            this.articleMapper.updateById(article);
        }

        //TODO 发布通知(暂时不考虑分布式事务)
        createNotifyByComment(commentCreateDTO);
    }

    /**
     * 发布通知
     * @param commentCreateDTO  评论创建实体
     */
    private void createNotifyByComment(CommentCreateDTO commentCreateDTO) {
        Integer notifierId = commentCreateDTO.getCommentatorId();
        Integer receiverId = null;
        Integer outerId = null;
        String type = null;
        String notifierName = null;
        UserDTO userDTO = this.userFeignClient.findById(commentCreateDTO.getCommentatorId());
        String outerTitle = null;
        if (userDTO != null) {
            notifierName = userDTO.getName();
        }
        // 如果是一级评论
        if (commentCreateDTO.getType().equals(CommentTypeEnum.ARTICLE.getType())) {
            Article article = this.articleMapper.selectById(commentCreateDTO.getArticleId());
            if (article != null) {
                receiverId = article.getUserId();
                outerId = article.getId();
                type = NotificationTypeEnum.COMMENT_ARTICLE.name();
                outerTitle = article.getTitle();
            }
        } else { //如果是二级评论
            Comment comment = this.commentMapper.selectById(commentCreateDTO.getParentId());
            if (comment != null) {
                receiverId = comment.getCommentatorId();
                outerId = comment.getId();
                type = NotificationTypeEnum.REPLY_COMMENT.name();
                outerTitle = comment.getContent();
            }
        }

        if (Objects.equals(notifierId,receiverId)) {
            return;
        }

        NotificationCreateDTO notificationCreateDTO = NotificationCreateDTO.builder()
                .notifierId(notifierId)
                .receiverId(receiverId)
                .outerId(outerId)
                .type(type)
                .notifierName(notifierName)
                .outerTitle(outerTitle)
                .content(commentCreateDTO.getContent())
                .createTime(System.currentTimeMillis())
                .build();
        //发送消息
        this.notificationSource.notificationOutput().send(MessageBuilder.withPayload(notificationCreateDTO).build());
    }


    /**
     * 根据文章id查找其文章下的所有评论
     * @param articleId 文章id
     * @return 评论详情集合
     */
    public List<CommentDTO> getCommentList(Integer articleId) {
        //1. 根据条件查询评论列表
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        List<Comment> commentList = this.commentMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(commentList)) {
            return null;
        }

        //1. 根据点赞数和创建时间从大到小排序评论列表
        commentList = commentList.stream()
                .sorted(Comparator.comparingInt(Comment::getLikeCount).reversed())
                .sorted(Comparator.comparingLong(Comment::getCreateTime).reversed())
                .collect(Collectors.toList());

        //2. 分离出去重的评论人
        Map<Integer,UserDTO> userDTOMap = this.getUserDTOMapByCommentList(commentList);

        //3.1 分离出一级评论
        List<Comment> commentFirstList = commentList.stream()
                .filter(comment -> comment.getType().equals(1)).collect(Collectors.toList());
        //3.2 一级评论 commentList -> commentDTOList
        List<CommentDTO> commentFirstDTOList = commentFirstList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUserDTO(userDTOMap.get(commentDTO.getCommentatorId()));
            return commentDTO;
        }).collect(Collectors.toList());

        //4.1 分离出二级评论
        List<Comment> commentSecondList = commentList.stream()
                .filter(comment -> comment.getType().equals(2)).collect(Collectors.toList());
        //4.2 二级评论 commentList -> commentDTOList
        List<CommentDTO> commentSecondDTOList = commentSecondList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUserDTO(userDTOMap.get(commentDTO.getCommentatorId()));
            return commentDTO;
        }).collect(Collectors.toList());

        //5. 通过commentListId区分,把commentSecondDTOList装载到对应的commentFirstDTOList中
        commentFirstDTOList = commentFirstDTOList.stream().peek(commentFirst ->{
            List<CommentDTO> collect = commentSecondDTOList.stream()
                    .filter(commentDTO -> commentDTO.getCommentListId().equals(commentFirst.getId()))
                    .collect(Collectors.toList());
            commentFirst.setCommentSecondDTOList(collect);
        }).collect(Collectors.toList());

        return commentFirstDTOList;
    }
    /**
     * 根据评论列表获取去重的评论人信息
     * @param commentList 论列表
     * @return 评论人信息Map
     */
    private Map<Integer,UserDTO> getUserDTOMapByCommentList(List<Comment> commentList) {
        //获取去重的评论人id
        List<Integer> userIdList = commentList.stream()
                .map(Comment::getCommentatorId).distinct()
                .collect(Collectors.toList());
        //根据id获取二级评论的用户信息表,并转换为map
        List<UserDTO> userDTOSecondList = this.userFeignClient.findListByIds(userIdList);
        return userDTOSecondList.stream().collect(Collectors.toMap(UserDTO::getId,userDTO -> userDTO));
    }


    /**
     * 删除评论.
     *
     * @param id 评论id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Comment comment = this.commentMapper.selectById(id);
        //计算被应该减去的评论数 count
        Integer count = comment.getType().equals(CommentTypeEnum.ARTICLE.getType())?(comment.getCommentCount() + 1):1;
        //删除该评论
        this.commentMapper.deleteById(id);
        //如果被删除的是二级评论
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //更新数据库
            // 更新评论列表的回复数
            Comment listComment = this.commentMapper.selectById(comment.getCommentListId());
            listComment.setCommentCount(listComment.getCommentCount() - 1);
            this.commentMapper.updateById(listComment);

            //更新父评论的回复数
            if (!comment.getCommentListId().equals(comment.getParentId())) {
                Comment parentComment = this.commentMapper.selectById(comment.getParentId());
                parentComment.setCommentCount(parentComment.getCommentCount() - 1);
                this.commentMapper.updateById(parentComment);
            }
        }

        //更新文章的评论数
        //文章评论数显示的是总的评论数,所以总评论数要 -count
        //看缓存中是否有文章
        ArticleDTO articleDTO = getArticleFromCache(comment.getArticleId());
        if (articleDTO != null) {
            //更新缓存文章评论数 +1
            articleDTO.setCommentCount(articleDTO.getCommentCount() - count);
            this.redisTemplate.opsForValue().set(ARTICLE_ID_KEY + comment.getArticleId(), articleDTO, 7L, TimeUnit.DAYS);
            //更新数据库
            Article article = new Article();
            BeanUtils.copyProperties(articleDTO, article);
            this.articleMapper.updateById(article);
            return;
        }
        //缓存中没有文章的话,直接更新数据库 总评论数 -count
        Article article = this.articleMapper.selectById(comment.getArticleId());
        article.setCommentCount(article.getCommentCount() - count);
        this.articleMapper.updateById(article);
    }

    /**
     * 从缓存中查找文章详情
     *
     * @param id 文章id
     * @return 文章详情
     */
    private ArticleDTO getArticleFromCache(Integer id) {
        String key = ARTICLE_ID_KEY + id;
        return (ArticleDTO) this.redisTemplate.opsForValue().get(key);
    }
}
