package com.emlyn.spring.transfer.error;

import com.emlyn.spring.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransferErrorCode implements ErrorCode {

    RECHARGE_NOT_FOUND("80101", "recharge not found"),
    RECHARGE_IDEMPOTENT("80102", "recharge idempotent"),
    RECHARGE_STATUS_INVALID("80103", "recharge status invalid")
    ;

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return display();
    }

}
