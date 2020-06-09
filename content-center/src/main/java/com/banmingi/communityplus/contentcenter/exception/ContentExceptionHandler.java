package com.banmingi.communityplus.contentcenter.exception;

import com.banmingi.communityplus.commons.exception.ExceptionBody;
import com.banmingi.communityplus.commons.exception.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 半命i 2020/6/9
 * @description
 */
@Slf4j
@RestControllerAdvice
public class ContentExceptionHandler {
    /**
     * 认证授权异常.
     * @param e
     * @return
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ExceptionBody> securityException(SecurityException e) {
        log.warn("发生Security异常",e);
        return new  ResponseEntity<>(
                ExceptionBody.builder()
                        .body(e.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }
}

