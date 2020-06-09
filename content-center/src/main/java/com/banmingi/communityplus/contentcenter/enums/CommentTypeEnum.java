package com.banmingi.communityplus.contentcenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 半命i 2020/5/25
 * @description 评论类型
 */
@Getter
@AllArgsConstructor
public enum CommentTypeEnum {
    /**
     * 一级评论,针对文章的评论
     */
    ARTICLE(1),
    /**
     * 二级评论,针对评论的回复
     */
    COMMENT(2);
    private final Integer type;
}
