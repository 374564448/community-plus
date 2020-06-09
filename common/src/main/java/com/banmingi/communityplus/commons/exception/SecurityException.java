package com.banmingi.communityplus.commons.exception;

/**
 * @author 半命i 2020/6/9
 * @description
 */
public class SecurityException extends RuntimeException {

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}

