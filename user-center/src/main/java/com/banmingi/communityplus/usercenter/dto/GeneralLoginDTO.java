package com.banmingi.communityplus.usercenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/4
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralLoginDTO implements Serializable {
    private static final long serialVersionUID = -6816484745478886737L;
    private String accountId;
    private String password;
}
