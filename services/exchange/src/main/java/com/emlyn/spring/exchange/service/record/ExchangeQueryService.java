package com.emlyn.spring.exchange.service.record;

import com.emlyn.spring.data.domain.entity.Exchange;

public interface ExchangeQueryService {

    Exchange queryByTradeId(String tradeId);

    Exchange queryByExchangeId(String exchangeId);

}
