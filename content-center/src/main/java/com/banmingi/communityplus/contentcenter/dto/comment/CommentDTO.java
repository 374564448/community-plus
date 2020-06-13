package com.banmingi.communityplus.contentcenter.dto.comment;

import com.banmingi.communityplus.contentcenter.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 半命i 2020/6/12
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = -5504566050479481605L;

    /**
     * id
     */
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 评论列表的id：
     * 比如一级评论列表comment_list_id是该文章id,
     * 二级评论列表是对应一级评论的id(该一级评论下有多条二级评论,但二级评论的parentId不仅仅只是该一级评论,也可能是该一级评论下的其他二级评论)
     */
    private Integer commentListId;

    /**
     * 该评论的父id: 文章id或者评论id,根据type来区分
     */
    private Integer parentId;

    /**
     * 评论类型：1表示针对文章的评论,2表示针对评论的回复
     */
    private Integer type;

    /**
     * 评论者id
     */
    private Integer commentatorId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 该文章或者该一级评论的评论数
     */
    private Integer commentCount;

    /**
     * 创建时间.
     */
    private Long createTime;

    /**
     * 评论者
     */
    private UserDTO userDTO;


}
