package com.emlyn.ma.my.pay.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public CommonResponse<?> handle(CommonException exception) {
        log.error("{}", exception.getErrorCode(), exception);
        return CommonResponse.failure(exception.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handle(Exception exception) {
        log.error("{}, massage:{}", ErrorCodeEnum.UNKNOWN, exception.getMessage(), exception);
        return CommonResponse.failure(ErrorCodeEnum.UNKNOWN);
    }

}
