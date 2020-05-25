package com.banmingi.communityplus.usercenter.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/9
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO implements Serializable {
    private static final long serialVersionUID = 5945994613177079980L;
    private String accountId;
    private String password;
    private String checkCode;
}
