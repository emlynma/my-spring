package com.emlyn.spring.trade.domain.model;

import com.emlyn.spring.data.domain.enums.ExchangeType;
import com.emlyn.spring.data.domain.enums.TradeType;
import lombok.Data;

@Data
public class ExchangeParams {

    private String tradeId;

    private Long amount;
    private String currency;
    private TradeType tradeType;
    private ExchangeType exchangeType;

}
