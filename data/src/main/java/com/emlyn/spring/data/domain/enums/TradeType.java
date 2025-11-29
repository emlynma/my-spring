package com.emlyn.spring.data.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeType {

    PAYMENT(10, "PAYMENT"),
    REFUND(20, "REFUND"),
    RECHARGE(30, "RECHARGE"),
    WITHDRAW(40, "WITHDRAW"),
    TRANSFER(50, "TRANSFER")
    ;

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;

}
