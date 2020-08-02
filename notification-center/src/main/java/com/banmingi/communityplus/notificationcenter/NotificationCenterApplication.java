package com.banmingi.communityplus.notificationcenter;

import com.banmingi.communityplus.notificationcenter.rocketmq.input.CheckCodeSink;
import com.banmingi.communityplus.notificationcenter.rocketmq.input.NotificationSink;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;


/**
 * @auther 半命i 2020/5/7
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({CheckCodeSink.class, NotificationSink.class})
@MapperScan("com.banmingi.communityplus.notificationcenter.mapper")
public class NotificationCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationCenterApplication.class);
    }

}
