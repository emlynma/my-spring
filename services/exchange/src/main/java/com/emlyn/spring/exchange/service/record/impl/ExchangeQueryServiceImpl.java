package com.emlyn.spring.exchange.service.record.impl;

import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.data.repository.ExchangeRepository;
import com.emlyn.spring.exchange.service.record.ExchangeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ExchangeQueryServiceImpl implements ExchangeQueryService {

    private final ExchangeRepository exchangeRepository;

    @Override
    public Exchange queryByTradeId(String tradeId) {
        if (!StringUtils.hasText(tradeId)) {
            return null;
        }
        Exchange condition = new Exchange();
        condition.setTradeId(tradeId);
        return exchangeRepository.selectOne(condition);
    }

    @Override
    public Exchange queryByExchangeId(String exchangeId) {
        if (!StringUtils.hasText(exchangeId)) {
            return null;
        }
        Exchange condition = new Exchange();
        condition.setExchangeId(exchangeId);
        return exchangeRepository.selectOne(condition);
    }

}
