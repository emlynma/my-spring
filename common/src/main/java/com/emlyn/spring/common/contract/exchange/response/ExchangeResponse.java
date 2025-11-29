package com.emlyn.spring.common.contract.exchange.response;

import lombok.Data;

@Data
public class ExchangeResponse {

    private String exchangeId;

    private String status;

    private String errorCode;

    private String errorDesc;

}
