package com.emlynma.spring.core.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseErrorCode implements ErrorCode {

    SUCCESS("00000", "success"),

    // client error
    CLIENT_ERROR("C0000", "client error"),
    PARAM_ERROR("C0001", "param error"),

    // system error
    SYSTEM_ERROR("S0000", "system error"),
    DATABASE_ERROR("S0100", "database error"),
    REDIS_ERROR("S0200", "redis error"),
    MQ_ERROR("S0300", "message queue error"),

    // third party error
    THIRD_PARTY_ERROR("T0000", "third party error"),

    // business error
    BUSINESS_ERROR("B0000", "business error"),

    UNKNOWN("-1", "unknown");

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return code + ", " + desc;
    }

}
