package com.banmingi.communityplus.usercenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther 半命i 2020/5/10
 * @description
 */
@Getter
@AllArgsConstructor
public enum  RegisterRespStatusEnum {
    SUCCESS(0), //成功
    CHECK_CODE_ERROR(1), //验证码错误
    ACCOUNT_BEING(2); //用户已存在
    private final Integer status;
}
