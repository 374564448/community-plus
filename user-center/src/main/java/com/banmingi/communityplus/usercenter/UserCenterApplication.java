package com.banmingi.communityplus.usercenter;

import com.banmingi.communityplus.usercenter.rocketmq.input.AddBonusSink;
import com.banmingi.communityplus.usercenter.rocketmq.output.CheckCodeSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author 半命i 2020/5/1
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.banmingi.communityplus.usercenter.mapper")
@EnableBinding({CheckCodeSource.class, AddBonusSink.class})
public class UserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class);
    }
}
