package com.banmingi.communityplus.contentcenter.service;

import com.banmingi.communityplus.contentcenter.dto.article.ArticleDTO;
import com.banmingi.communityplus.contentcenter.dto.comment.CommentCreateDTO;
import com.banmingi.communityplus.contentcenter.dto.comment.CommentDTO;
import com.banmingi.communityplus.contentcenter.dto.user.UserDTO;
import com.banmingi.communityplus.contentcenter.entity.Article;
import com.banmingi.communityplus.contentcenter.entity.Comment;
import com.banmingi.communityplus.contentcenter.enums.CommentTypeEnum;
import com.banmingi.communityplus.contentcenter.feignclient.UserFeignClient;
import com.banmingi.communityplus.contentcenter.mapper.ArticleMapper;
import com.banmingi.communityplus.contentcenter.mapper.CommentMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    private static final String ARTICLE_ID_KEY = "article:id:";

    /**
     * 评论
     *
     * @param commentCreateDTO 实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void comment(CommentCreateDTO commentCreateDTO) {
        //1. 构建评论实体
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        comment.setCreateTime(System.currentTimeMillis());

        //2. 评论插入数据库
        this.commentMapper.insert(comment);

        //3. 如果是二级评论的话,针对这条评论的评论数要 +1
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

        //4. 文章评论数显示的是总的评论数,所以要 +1
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
            return;
        }
        //缓存中没有文章的话,直接更新数据库
        Article article = this.articleMapper.selectById(comment.getArticleId());
        article.setCommentCount(article.getCommentCount() + 1);
        this.articleMapper.updateById(article);

    }


    /**
     * 获取评论列表.
     *
     * @param commentListId 响应评论列表集合的id：
     * @param type          类型
     * @return 评论列表
     */
    public List<CommentDTO> getCommentList(Integer commentListId, Integer type) {
        //1. 根据条件查询评论列表
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_list_id", commentListId).eq("type", type);
        List<Comment> commentList = this.commentMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(commentList)) {
            return null;
        }

        //2. 根据点赞数和创建时间从打到小排序评论列表
        commentList = commentList.stream()
                .sorted(Comparator.comparingInt(Comment::getLikeCount).reversed())
                .sorted(Comparator.comparingLong(Comment::getCreateTime).reversed())
                .collect(Collectors.toList());

        //3. 获取去重的评论人
        List<Integer> commentatorIdList
                = commentList.stream().map(Comment::getCommentatorId).distinct()
                .collect(Collectors.toList());

        //4. 根据commentatorIdList获取评论人信息列表,并转换为map
        List<UserDTO> userDTOList = this.userFeignClient.findListByIds(commentatorIdList);
        Map<Integer, UserDTO> userDTOMap =
                userDTOList.stream().collect(Collectors.toMap(UserDTO::getId, userDTO -> userDTO));

        //5. 转换 commentList 为commentDTOList并返回
        return commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUserDTO(userDTOMap.get(commentDTO.getCommentatorId()));
            return commentDTO;
        }).collect(Collectors.toList());
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
