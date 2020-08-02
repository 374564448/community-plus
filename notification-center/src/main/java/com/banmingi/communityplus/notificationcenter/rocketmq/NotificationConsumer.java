package com.banmingi.communityplus.notificationcenter.rocketmq;

import com.banmingi.communityplus.notificationcenter.dto.notification.NotificationCreateDTO;
import com.banmingi.communityplus.notificationcenter.rocketmq.input.NotificationSink;
import com.banmingi.communityplus.notificationcenter.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @author 半命i 2020/8/2
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationConsumer {

    private final NotificationService notificationService;

    /**
     * 创建通知
     * @param notificationCreateDTO 从RocketMQ获取的通知创建实体类
     */
    @StreamListener(NotificationSink.NOTIFICATION_INPUT)
    public void createNotification(NotificationCreateDTO notificationCreateDTO) {
        this.notificationService.createNotification(notificationCreateDTO);
    }

}
