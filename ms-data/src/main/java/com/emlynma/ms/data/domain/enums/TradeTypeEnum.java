package com.emlynma.ms.data.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeTypeEnum {

    TRADE(40, "trade"),
    REFUND(50, "refund"),
    ;

    private final Integer code;
    private final String desc;

}
