package com.banmingi.communityplus.contentcenter.dto.usercenter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/19
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 512676895831960252L;
    /**
     * id
     */
    private Integer id;

    /**
     * 账户类型
     */
    private Integer accountType;

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
