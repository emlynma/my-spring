package com.emlyn.spring.transfer.domain.model;

import com.emlyn.spring.data.domain.enums.status.ExchangeStatus;
import lombok.Data;

@Data
public class ExchangeResult {

    private String tradeId;
    private String exchangeId;

    private ExchangeStatus status;

    private String errorCode;
    private String errorDesc;

}
