package com.banmingi.communityplus.contentcenter.service;

import com.banmingi.communityplus.contentcenter.dto.article.ArticleDTO;
import com.banmingi.communityplus.contentcenter.dto.comment.CommentCreateDTO;
import com.banmingi.communityplus.contentcenter.entity.Article;
import com.banmingi.communityplus.contentcenter.entity.Comment;
import com.banmingi.communityplus.contentcenter.enums.CommentTypeEnum;
import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import com.banmingi.communityplus.contentcenter.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
    private final RedisTemplate<String,Object> redisTemplate;

    private static final String ARTICLE_ID_KEY = "article:id:";

    /**
     * 评论
     * @param commentCreateDTO 实体
     */
    public void comment(CommentCreateDTO commentCreateDTO) {
        //1. 构建评论实体
        Comment comment = Comment.builder()
                .parentId(commentCreateDTO.getParentId())
                .type(commentCreateDTO.getType())
                .commentatorId(commentCreateDTO.getCommentatorId())
                .content(commentCreateDTO.getContent())
                .createTime(System.currentTimeMillis())
                .build();

        //2. 针对文章的评论
        if(comment.getType().equals(CommentTypeEnum.ARTICLE.getType())) {
            this.commentMapper.insert(comment);
            //看缓存中是否有文章
            String key = ARTICLE_ID_KEY + comment.getParentId();
            ArticleDTO articleDTO =
                    (ArticleDTO) this.redisTemplate.opsForValue().get(key);
            if (articleDTO != null) {
                //更新缓存文章评论数 +1
                articleDTO.setCommentCount(articleDTO.getCommentCount() + 1);
                this.redisTemplate.opsForValue().set(key,articleDTO,7L, TimeUnit.DAYS);
                //更新数据库
                Article article = new Article();
                BeanUtils.copyProperties(articleDTO,article);
                this.articleMapper.updateById(article);
                return;
            }
            //缓存中没有文章的话,直接更新数据库
            Article article = this.articleMapper.selectById(comment.getParentId());
            article.setCommentCount(article.getCommentCount() + 1);
            this.articleMapper.updateById(article);
        } else {//3. 针对评论的回复
            this.commentMapper.insert(comment);
            //更新数据库
            Comment parentComment = this.commentMapper.selectById(comment.getParentId());
            parentComment.setCommentCount(parentComment.getCommentCount() + 1);
            this.commentMapper.updateById(parentComment);
        }

    }
}
