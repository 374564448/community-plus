package com.banmingi.communityplus.cloudcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CloudCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCenterApplication.class);
    }
}
