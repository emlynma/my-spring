package com.emlynma.spring.data.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthTypeEnum {

    LOCAL(0, "本地"),
    THIRD(1, "三方");

    @EnumValue
    @JsonValue
    private final int code;
    private final String desc;

}
