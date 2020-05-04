package com.banmingi.communityplus.usercenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账户id
     */
    private String accountId;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 密码
     */
    private String password;

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
     * 角色
     */
    private String roles;

    /**
     * 用户积分
     */
    private Integer bonus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

}
