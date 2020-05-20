package com.banmingi.communityplus.usercenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/1
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespDTO implements Serializable {

    private static final long serialVersionUID = -479280076325178110L;
    /**
     * token
     */
    private JwtTokenRespDTO token;
    /**
     * 用户信息
     */
    private UserDTO user;
}
