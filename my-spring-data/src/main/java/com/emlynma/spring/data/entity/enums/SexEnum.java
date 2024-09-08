package com.emlynma.spring.data.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum {

    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    @JsonValue
    private final int code;
    private final String desc;

}
