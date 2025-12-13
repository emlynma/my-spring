package com.emlyn.spring.trade.service.data.exchange.impl;

import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.data.repository.ExchangeRepository;
import com.emlyn.spring.trade.service.data.exchange.ExchangeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeQueryServiceImpl implements ExchangeQueryService {

    private final ExchangeRepository exchangeRepository;

    @Override
    public List<Exchange> queryByTradeId(String tradeId) {
        Exchange condition = new Exchange();
        condition.setTradeId(tradeId);
        return exchangeRepository.select(condition);
    }

}
