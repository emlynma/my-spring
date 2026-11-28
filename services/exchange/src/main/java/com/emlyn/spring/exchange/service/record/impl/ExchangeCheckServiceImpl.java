package com.emlyn.spring.exchange.service.record.impl;

import com.emlyn.spring.common.util.AssertUtils;
import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.exchange.constant.ExchangeErrorCode;
import com.emlyn.spring.exchange.exception.IdempotentException;
import com.emlyn.spring.exchange.service.record.ExchangeCheckService;
import com.emlyn.spring.exchange.service.record.ExchangeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeCheckServiceImpl implements ExchangeCheckService {

    private final ExchangeQueryService exchangeQueryService;

    @Override
    public void checkCreateIdempotency(String exchangeId) {
        Exchange exchange = exchangeQueryService.queryByExchangeId(exchangeId);
        if (exchange != null) {
            throw new IdempotentException(ExchangeErrorCode.EXCHANGE_IDEMPOTENT, exchange);
        }
    }

    @Override
    public void checkStatusIdempotency(String exchangeId) {
        Exchange exchange = exchangeQueryService.queryByExchangeId(exchangeId);
        AssertUtils.isTrue(exchange != null, ExchangeErrorCode.EXCHANGE_NOT_FOUND);
        if (exchange.isCompleted()) {
            throw new IdempotentException(ExchangeErrorCode.EXCHANGE_IDEMPOTENT, exchange);
        }
    }

}
