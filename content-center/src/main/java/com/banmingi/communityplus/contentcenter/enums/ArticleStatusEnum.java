package com.banmingi.communityplus.contentcenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {
    NOT_YET(0),
    PASS(1),
    REJECT(2);

    private Integer status;

}
