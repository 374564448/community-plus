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
    private final RedisTemplate<String,Object> redisTemplate;
    private final UserFeignClient userFeignClient;

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


    /**
     * 获取评论列表.
     * @param parentId 列表parentId
     * @param type 类型
     * @return 评论列表
     */
    public List<CommentDTO> getCommentList(Integer parentId, Integer type) {
        //1. 根据条件查询评论列表
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId).eq("type",type);
        List<Comment> commentList = this.commentMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(commentList)) {
            return null;
        }

        //2. 根据创建时间从打到小排序评论列表
        commentList = commentList.stream()
                .sorted(Comparator.comparingLong(Comment::getCreateTime).reversed())
                .collect(Collectors.toList());

        //3. 获取去重的评论人
        List<Integer> commentatorIdList
                = commentList.stream().map(Comment::getCommentatorId).distinct()
                .collect(Collectors.toList());

        //4. 根据commentatorIdList获取评论人信息列表,并转换为map
        List<UserDTO> userDTOList = this.userFeignClient.findListByIds(commentatorIdList);
        Map<Integer,UserDTO> userDTOMap =
                userDTOList.stream().collect(Collectors.toMap(UserDTO::getId, userDTO ->userDTO));

        //5. 转换 commentList 为commentDTOList并返回
        return commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUserDTO(userDTOMap.get(commentDTO.getCommentatorId()));
            return commentDTO;
        }).collect(Collectors.toList());
    }
}
