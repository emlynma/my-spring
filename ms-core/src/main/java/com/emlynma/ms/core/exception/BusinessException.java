package com.emlynma.ms.core.exception;

import com.emlynma.ms.core.base.BaseException;
import com.emlynma.ms.core.base.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends BaseException {

    /**
     * 业务数据
     */
    private final Object data;

    public BusinessException(ErrorCode errorCode, Object data) {
        super(errorCode);
        this.data = data;
    }

}
