package com.emlyn.spring.exchange.constant;

import com.emlyn.spring.common.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExchangeErrorCode implements ErrorCode {

    EXCHANGE_NOT_FOUND("80201", "exchange not found"),
    EXCHANGE_IDEMPOTENT("80202", "exchange idempotent"),
    EXCHANGE_STATUS_INVALID("80203", "exchange status invalid")
    ;

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return display();
    }

}
