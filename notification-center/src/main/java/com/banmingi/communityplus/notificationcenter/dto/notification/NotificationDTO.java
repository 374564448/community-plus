package com.banmingi.communityplus.notificationcenter.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 半命i
 * @date 2020/09/12 22:07
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO implements Serializable {
    private static final long serialVersionUID = 1120935139433279190L;
    /**
     * id
     */
    private Integer id;

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
     * 通知的类型（回复了你的评论、评论了你的文章、点赞了你等等）
     */
    private String typeDesc;


    /**
     * 状态（已读1 / 未读0）
     */
    private Boolean status;

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
