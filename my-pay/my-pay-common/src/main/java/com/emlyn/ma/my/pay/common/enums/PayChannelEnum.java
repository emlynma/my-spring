package com.emlyn.ma.my.pay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayChannelEnum {

    WE_CHAT_PAY(16, "微信支付"),
    ALI_PAY(32, "支付包")
    ;

    private final int code;
    private final String desc;

}
