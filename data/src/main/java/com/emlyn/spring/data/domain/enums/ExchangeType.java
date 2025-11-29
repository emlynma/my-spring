package com.emlyn.spring.data.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExchangeType {

    POSTING(1, "POSTING"),
    REVERSE(2, "REVERSE"),
    ;

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String  desc;

}
