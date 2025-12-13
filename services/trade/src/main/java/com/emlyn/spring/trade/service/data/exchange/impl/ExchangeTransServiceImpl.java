package com.emlyn.spring.trade.service.data.exchange.impl;

import com.emlyn.spring.data.component.id.TradeIdGenerator;
import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.data.repository.ExchangeRepository;
import com.emlyn.spring.trade.service.data.exchange.ExchangeTransService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeTransServiceImpl implements ExchangeTransService {

    private final ExchangeRepository exchangeRepository;

    private final TradeIdGenerator tradeIdGenerator;

    @Override
    public Exchange createExchange(Exchange exchange) {
        exchange.setExchangeId(tradeIdGenerator.generateExchangeIdWithTradeId(exchange.getTradeId()));
        exchangeRepository.insert(exchange);
        return exchange;
    }

}
