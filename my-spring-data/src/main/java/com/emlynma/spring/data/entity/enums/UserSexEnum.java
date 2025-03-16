package com.emlynma.spring.data.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSexEnum {

    FEMALE(0, "女"),
    MALE(1, "男"),
    ;

    @EnumValue
    @JsonValue
    private final int code;
    private final String desc;

}
