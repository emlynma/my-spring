package com.emlyn.spring.data.domain.enums.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeType {

    RECHARGE(10, "RECHARGE"),
    WITHDRAW(20, "WITHDRAW"),
    ;

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;

}
