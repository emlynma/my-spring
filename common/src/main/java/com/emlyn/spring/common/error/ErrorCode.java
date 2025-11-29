package com.emlyn.spring.common.error;

public interface ErrorCode {

    String getCode();

    String getDesc();

    default String display() {
        return getDesc() + " (" + getCode() + ")";
    }

}
