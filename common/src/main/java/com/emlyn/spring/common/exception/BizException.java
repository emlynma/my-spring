package com.emlyn.spring.common.exception;

import com.emlyn.spring.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

}
