package com.banmingi.communityplus.contentcenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 半命i 2020/6/13
 * @description
 */
@Getter
@AllArgsConstructor
public enum CacheDataToDbTypeEnum {
    ARTICLE("ARTICLE","缓存文章详情更新到数据"),
    COMMENT_LIKE_COUNT("COMMENT_LIKE_COUNT","缓存评论点赞数更新到数据库");
    private final String event;
    private final String description;
}
