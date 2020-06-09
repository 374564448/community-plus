package com.banmingi.communityplus.commons.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @auther 半命i 2020/4/23
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthorization {
    String value();
}
