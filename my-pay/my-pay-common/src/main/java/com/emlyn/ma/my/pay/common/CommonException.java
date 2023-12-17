package com.emlyn.ma.my.pay.common;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final ErrorCodeEnum errorCode;

    public CommonException(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    public CommonException(ErrorCodeEnum errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
