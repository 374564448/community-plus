package com.banmingi.communityplus.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 半命i 2020/6/14
 * @description
 */
@Getter
@AllArgsConstructor
public enum  NotificationTypeEnum {
    COMMENT_ARTICLE("评论了你的文章"),
    LIKE_ARTICLE("点赞了你的文章"),
    COLLECT_ARTICLE("收藏了你的文章"),
    REPLY_COMMENT("回复了你的评论"),
    FOLLOW_YOU("关注了你");

    private final String desc;
}
