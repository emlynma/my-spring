package com.emlynma.spring.data.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    NORMAL(0, "正常"),
    DISABLED(1, "禁用");

    @EnumValue
    @JsonValue
    private final int code;
    private final String desc;

}
