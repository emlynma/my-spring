package com.emlyn.spring.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnvEnum {

    DEV("dev", "dev"),
    TEST("test", "test"),
    PROD("prod", "prod");

    private final String code;
    private final String desc;

}
