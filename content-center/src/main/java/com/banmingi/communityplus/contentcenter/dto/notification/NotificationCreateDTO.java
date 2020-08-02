package com.banmingi.communityplus.contentcenter.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 半命i 2020/8/2
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationCreateDTO {
    /**
     * 发出通知的人的id
     */
    private Integer notifierId;

    /**
     * 接收通知的人的id
     */
    private Integer receiverId;

    /**
     * 被通知的内容的id （问题id 、评论id 等，通过type来区分）
     */
    private Integer outerId;

    /**
     * 通知的类型（回复了你的评论、评论了你的文章、点赞了你等等）
     */
    private String type;

    /**
     * 发出通知的人的名字
     */
    private String notifierName;

    /**
     * 被通知的内容(文章标题、评论内容等)
     */
    private String outerTitle;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Long createTime;

}
