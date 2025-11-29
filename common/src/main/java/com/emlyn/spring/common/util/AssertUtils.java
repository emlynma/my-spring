package com.emlyn.spring.common.util;

import com.emlyn.spring.common.error.ErrorCode;
import com.emlyn.spring.common.exception.BizException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssertUtils {

    public static void isTrue(boolean expression, RuntimeException exception) {
        if (!expression) {
            throw exception;
        }
    }

    public static void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BizException(errorCode);
        }
    }

}
