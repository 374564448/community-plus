package com.banmingi.communityplus.contentcenter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther 半命i 2020/5/13
 * @description 文章审核状态.
 */
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {
    NOT_YET,
    PASS,
    REJECT;
}
