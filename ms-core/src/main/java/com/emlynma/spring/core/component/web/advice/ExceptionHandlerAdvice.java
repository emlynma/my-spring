package com.emlynma.spring.core.component.web.advice;

import com.emlynma.spring.core.base.ApiResponse;
import com.emlynma.spring.core.base.BaseErrorCode;
import com.emlynma.spring.core.base.BaseException;
import com.emlynma.spring.core.base.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

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
