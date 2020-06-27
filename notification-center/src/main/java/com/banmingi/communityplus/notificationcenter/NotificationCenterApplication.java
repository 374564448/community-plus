package com.banmingi.communityplus.notificationcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;


/**
 * @auther 半命i 2020/5/7
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Sink.class)
public class NotificationCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationCenterApplication.class);
    }

}
