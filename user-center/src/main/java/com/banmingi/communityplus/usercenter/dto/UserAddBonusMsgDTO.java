package com.banmingi.communityplus.usercenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/4/18
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddBonusMsgDTO implements Serializable {

    private static final long serialVersionUID = 8965119563399371260L;
    /**
     * 为谁加积分
     */
    private Integer userId;
    /**
     * 加多少积分
     */
    private Integer bonus;

    /**
     * 描述
     */
    private String description;

    /**
     * 事件
     */
    private String event;
}
