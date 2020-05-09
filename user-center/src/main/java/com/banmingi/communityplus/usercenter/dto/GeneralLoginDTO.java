package com.banmingi.communityplus.usercenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 半命i 2020/5/4
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralLoginDTO {
    private String accountId;
    private String password;
}
