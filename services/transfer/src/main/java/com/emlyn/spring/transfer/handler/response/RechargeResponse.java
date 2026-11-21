package com.emlyn.spring.transfer.handler.response;

import com.emlyn.spring.data.domain.enums.status.RechargeStatus;
import lombok.Data;

@Data
public class RechargeResponse {

    private String tradeId;

    private RechargeStatus status;

    private String errorCode;
    private String errorDesc;

}
