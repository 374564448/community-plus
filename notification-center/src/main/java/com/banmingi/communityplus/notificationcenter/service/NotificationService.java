package com.banmingi.communityplus.notificationcenter.service;

import com.banmingi.communityplus.notificationcenter.dto.notification.NotificationCreateDTO;
import com.banmingi.communityplus.notificationcenter.entity.Notification;
import com.banmingi.communityplus.notificationcenter.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 半命i 2020/8/2
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationService {

    private final NotificationMapper notificationMapper;

    /**
     * 创建通知
     * @param notificationCreateDTO 从RocketMQ获取的通知创建实体类
     */
    public void createNotification(NotificationCreateDTO notificationCreateDTO) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationCreateDTO,notification);
        notification.setStatus(false);

        this.notificationMapper.insert(notification);

    }

}
