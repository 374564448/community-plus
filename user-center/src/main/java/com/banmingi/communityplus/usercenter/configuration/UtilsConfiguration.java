package com.banmingi.communityplus.usercenter.configuration;

import com.banmingi.communityplus.commons.utils.JwtOperator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@Configuration
public class UtilsConfiguration {

   // JWT
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire-time-in-second}")
    private Long expirationTimeInSecond;



    /**
     * JWT工具类注入
     * @return
     */
    @Bean
    public JwtOperator jwtOperator() {
        return new JwtOperator(secret,expirationTimeInSecond);
    }
}
