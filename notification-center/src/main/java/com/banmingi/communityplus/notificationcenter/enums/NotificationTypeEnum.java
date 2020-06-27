package com.banmingi.communityplus.notificationcenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 半命i 2020/6/14
 * @description
 */
@Getter
@AllArgsConstructor
public enum  NotificationTypeEnum {
    COMMENT_ARTICLE(1,"评论了你的文章"),
    LIKE_ARTICLE(2,"点赞了你的文章"),
    COLLECT_ARTICLE(3,"收藏了你的文章"),
    REPLY_COMMENT(4,"回复了你的评论"),
    FOLLOW_YOU(5,"关注了你");


    private final Integer type;
    private final String name;


    public static String nameOfType(Integer type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if(type.equals(notificationTypeEnum.getType())){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
