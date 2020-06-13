package com.banmingi.communityplus.contentcenter.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 半命i 2020/5/25
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDTO implements Serializable {
    private static final long serialVersionUID = 2701091807161694037L;

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
     * 评论者id
     */
    private Integer commentatorId;

    /**
     * 评论类型：1表示针对文章的评论,2表示针对评论的回复
     */
    private Integer type;

    /**
     * 评论内容
     */
    private String content;

}
