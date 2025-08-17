package com.emlynma.ms.core.base;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(errorCode.toString() + "|" + message);
        this.errorCode = errorCode;
    }

}
