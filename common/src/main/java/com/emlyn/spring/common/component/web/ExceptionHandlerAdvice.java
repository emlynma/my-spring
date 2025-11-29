package com.emlyn.spring.common.component.web;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.common.error.ErrorCode;
import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.exception.BizException;
import com.emlyn.spring.common.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BizException.class)
    public ApiResponse<?> handleException(BizException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.error("{}", errorCode, exception);
        return ApiResponse.error(errorCode);
    }

    @ExceptionHandler(SysException.class)
    public ApiResponse<?> handleException(SysException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.error("{}", errorCode, exception);
        return ApiResponse.error(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception exception) {
        log.error("{}", SysErrorCode.UNKNOWN, exception);
        return ApiResponse.error(SysErrorCode.UNKNOWN);
    }

}
