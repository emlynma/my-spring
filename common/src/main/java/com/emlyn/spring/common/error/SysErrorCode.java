package com.emlyn.spring.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysErrorCode implements ErrorCode {

    SUCCESS("200", "success"),
    UNKNOWN("500", "system error"),

    RPC_TIMEOUT("510", "rpc timeout"),
    RPC_FAILURE("511", "rpc failure"),

    DB_INSERT_ERROR("50011", "database insert error"),
    DB_UPDATE_ERROR("50012", "database update error"),
    DB_DELETE_ERROR("50013", "database delete error"),
    DB_SELECT_ERROR("50014", "database select error"),
    DB_LOCKED_ERROR("50015", "database locked error"),
    DB_UNLOCK_ERROR("50016", "database unlock error"),

    REDIS_GET_ERROR("50021", "redis get error"),
    REDIS_SET_ERROR("50022", "redis set error"),
    REDIS_EXE_ERROR("50023", "redis exe error"),

    MESSAGE_PRODUCE("50031", "message produce error"),
    MESSAGE_CONSUME("50032", "message consume error"),
    ;

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return display();
    }

}
