package com.emlynma.ms.data.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeTypeEnum {

    TRADE(40, "trade"),
    REFUND(50, "refund"),
    ;

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;

}
