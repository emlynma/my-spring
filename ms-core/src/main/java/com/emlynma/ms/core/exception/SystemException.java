package com.emlynma.ms.core.exception;

import com.emlynma.ms.core.base.BaseException;
import com.emlynma.ms.core.base.ErrorCode;
import lombok.Getter;

@Getter
public class SystemException extends BaseException {

    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SystemException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
