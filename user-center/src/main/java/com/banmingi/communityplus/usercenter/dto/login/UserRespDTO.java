package com.banmingi.communityplus.usercenter.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 简介
     */
    private String bio;

    /**
     * 用户积分
     */
    private Integer bonus;

}
