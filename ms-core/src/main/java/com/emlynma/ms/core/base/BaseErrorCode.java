package com.emlynma.ms.core.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseErrorCode implements ErrorCode {

    SUCCESS("00000", "success"),
    UNKNOWN("11111", "unknown"),

    // C: client error
    CLIENT_ERROR("C0000", "client error"),
    PARAM_ERROR("C0001", "param error"),

    // S: system error
    SYSTEM_ERROR("S0000", "system error"),

    DATABASE_ERROR ("S0100", "database error"),
    DB_SELECT_ERROR("S0101", "database select error"),
    DB_INSERT_ERROR("S0102", "database insert error"),
    DB_UPDATE_ERROR("S0103", "database update error"),
    DB_DELETE_ERROR("S0104", "database delete error"),
    DB_LOCK_ERROR("S0105", "database lock error"),
    DB_UNLOCK_ERROR("S0106", "database unlock error"),

    REDIS_ERROR("S0200", "redis error"),

    MQ_ERROR("S0300", "message queue error"),

    // T: third party error
    THIRD_PARTY_ERROR("T0000", "third party error"),

    // V: vendor error

    // B: business error
    BUSINESS_ERROR("B0000", "business error"),

    ;

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return "code:" + code + ", desc:" + desc;
    }

}
