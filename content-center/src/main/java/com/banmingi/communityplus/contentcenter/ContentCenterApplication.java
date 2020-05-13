package com.banmingi.communityplus.contentcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @auther 半命i 2020/5/12
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.banmingi.communityplus.contentcenter.mapper")
public class ContentCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class);
    }
}