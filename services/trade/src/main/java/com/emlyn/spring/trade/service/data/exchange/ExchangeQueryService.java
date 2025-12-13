package com.emlyn.spring.trade.service.data.exchange;

import com.emlyn.spring.data.domain.entity.Exchange;

import java.util.List;

public interface ExchangeQueryService {

    List<Exchange> queryByTradeId(String tradeId);

}
