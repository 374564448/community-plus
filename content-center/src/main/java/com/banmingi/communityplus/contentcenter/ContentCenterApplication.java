package com.banmingi.communityplus.contentcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @auther 半命i 2020/5/12
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.banmingi.communityplus.contentcenter.mapper")
@EnableBinding(Source.class)
@EnableFeignClients
public class ContentCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class);
    }
}
