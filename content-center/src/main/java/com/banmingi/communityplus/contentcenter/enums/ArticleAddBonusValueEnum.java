package com.banmingi.communityplus.contentcenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther 半命i 2020/5/17
 * @description 文章相关积分添加值
 */
@Getter
@AllArgsConstructor
public enum ArticleAddBonusValueEnum {
    CREATED(50,"CREATED","文章发布加积分"),
    LIKED(10,"LIKED","文章被点赞加积分"),
    COLLECTED(20,"COLLECTED","文章被收藏加积分");
    private final Integer value;
    private final String event;
    private final String description;
}
