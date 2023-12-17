package com.emlyn.ma.my.pay.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    SUCCESS("00000", "success"),
    UNKNOWN("11111", "unknown"),
    ;

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return "code: " + code + ", desc: " + desc;
    }

}
