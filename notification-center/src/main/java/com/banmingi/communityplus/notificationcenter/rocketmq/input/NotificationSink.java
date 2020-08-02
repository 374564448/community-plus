package com.banmingi.communityplus.notificationcenter.rocketmq.input;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author 半命i 2020/8/2
 * @description
 */
public interface NotificationSink {
    String NOTIFICATION_INPUT = "notification-input";

    @Input(NOTIFICATION_INPUT)
    SubscribableChannel notificationInput();
}
