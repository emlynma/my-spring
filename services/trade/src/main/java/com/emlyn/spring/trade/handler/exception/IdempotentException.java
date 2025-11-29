package com.emlyn.spring.trade.handler.exception;

import com.emlyn.spring.common.error.ErrorCode;
import com.emlyn.spring.common.exception.BizException;
import lombok.Getter;

@Getter
public class IdempotentException extends BizException {

    private final Object record;

    public IdempotentException(ErrorCode errorCode, Object record) {
        super(errorCode);
        this.record = record;
    }

}
