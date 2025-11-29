package com.emlyn.spring.trade.error;

import com.emlyn.spring.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TradeErrorCode implements ErrorCode {

    RECHARGE_NOT_FOUND("30001", "recharge not found"),
    RECHARGE_IDEMPOTENT("30002", "recharge idempotent"),
    RECHARGE_STATUS_INVALID("30003", "recharge status invalid")
    ;

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return display();
    }

}
