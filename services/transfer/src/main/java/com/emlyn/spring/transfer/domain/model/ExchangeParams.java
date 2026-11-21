package com.emlyn.spring.transfer.domain.model;

import com.emlyn.spring.data.domain.enums.type.ExchangeType;
import com.emlyn.spring.data.domain.enums.type.TradeType;
import lombok.Data;

@Data
public class ExchangeParams {

    private String tradeId;

    private Long amount;
    private String currency;
    private TradeType tradeType;
    private ExchangeType exchangeType;

}
