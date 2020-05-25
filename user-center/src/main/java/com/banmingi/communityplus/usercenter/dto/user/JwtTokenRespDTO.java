package com.banmingi.communityplus.usercenter.dto.user;

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
public class JwtTokenRespDTO implements Serializable {
    private static final long serialVersionUID = -8019319419919167830L;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Long expirationTime;
}
