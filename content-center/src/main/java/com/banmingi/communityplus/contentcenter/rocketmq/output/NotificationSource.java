package com.banmingi.communityplus.contentcenter.rocketmq.output;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author 半命i 2020/8/2
 * @description
 */
public interface NotificationSource {
    String NOTIFICATION_OUTPUT = "notification-output";

    @Output(NOTIFICATION_OUTPUT)
    MessageChannel notificationOutput();
}
