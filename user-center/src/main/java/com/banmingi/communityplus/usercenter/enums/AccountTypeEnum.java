package com.banmingi.communityplus.usercenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther 半命i 2020/5/10
 * @description
 */
@Getter
@AllArgsConstructor
public enum AccountTypeEnum {
    General(1),
    GitHub(2);
    private Integer accountType;
}
