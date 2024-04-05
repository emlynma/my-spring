package com.emlynma.spring.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseErrorCode implements ErrorCode {

    SUCCESS("0", "success"),

    PARAM_ERROR("10001", "param error"),

    UNKNOWN("-1", "unknown");

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return code + ", " + desc;
    }

}
