package com.emlynma.spring.core.web;

import com.emlynma.spring.core.ApiResponse;
import com.emlynma.spring.core.BaseErrorCode;
import com.emlynma.spring.core.BaseException;
import com.emlynma.spring.core.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ApiResponse<?> handleException(BaseException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.error("{}", errorCode, exception);
        return ApiResponse.error(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception exception) {
        log.error("{}", BaseErrorCode.UNKNOWN, exception);
        return ApiResponse.error(BaseErrorCode.UNKNOWN);
    }

}
