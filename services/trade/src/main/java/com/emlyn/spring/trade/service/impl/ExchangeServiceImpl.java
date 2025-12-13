package com.emlyn.spring.trade.service.impl;

import com.emlyn.spring.common.contract.exchange.model.AssetInfo;
import com.emlyn.spring.common.contract.exchange.request.PostingRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import com.emlyn.spring.data.component.id.TradeIdGenerator;
import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.data.domain.enums.status.ExchangeStatus;
import com.emlyn.spring.trade.domain.model.ExchangeParams;
import com.emlyn.spring.trade.domain.model.ExchangeResult;
import com.emlyn.spring.trade.service.ExchangeService;
import com.emlyn.spring.trade.service.data.exchange.ExchangeQueryService;
import com.emlyn.spring.trade.service.data.exchange.ExchangeTransService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeQueryService exchangeQueryService;
    private final ExchangeTransService exchangeTransService;
    private final TradeIdGenerator tradeIdGenerator;

    @Override
    public ExchangeResult exchange(ExchangeParams request) {

        Exchange exchange = queryExistingExchange(request);
        if (exchange == null) {
            exchange = exchangeTransService.createExchange(buildExchange(request));
        }

        if (exchange.isCompleted()) {
            return buildExchangeResponse(exchange);
        }

        // 执行资产交换

        return buildExchangeResponse(exchange);
    }

    @Override
    public void exchange(Exchange exchange) {

        PostingRequest request = new PostingRequest();
        request.setExchangeId(exchange.getExchangeId());
        request.setAmount(exchange.getAmount());
        request.setCurrency(exchange.getCurrency());
        request.setPayerInfo(new AssetInfo());
        request.setPayeeInfo(new AssetInfo());

        // RPC 调用
        ExchangeResponse response = new ExchangeResponse();

    }


    private Exchange queryExistingExchange(ExchangeParams request) {
        List<Exchange> exchangeList = exchangeQueryService.queryByTradeId(request.getTradeId());
        if (exchangeList.isEmpty()) {
            return null;
        }
        if (exchangeList.size() == 1) {
            return exchangeList.get(0);
        }
        return exchangeList.stream().max(Comparator.comparing(Exchange::getCreateTime)).get();
    }

    private Exchange buildExchange(ExchangeParams request) {
        Exchange exchange = new Exchange();
        exchange.setTradeId(request.getTradeId());
        exchange.setExchangeId(tradeIdGenerator.generateExchangeIdWithTradeId(request.getTradeId()));
//        exchange.setExchangeRequest();
//        exchange.setExchangeResponse();
        exchange.setAmount(request.getAmount());
        exchange.setCurrency(request.getCurrency());
        exchange.setTradeType(request.getTradeType());
        exchange.setExchangeType(request.getExchangeType());
        exchange.setStatus(ExchangeStatus.INITIAL);
        return exchange;
    }

    private ExchangeResult buildExchangeResponse(Exchange exchange) {
        ExchangeResult response = new ExchangeResult();
        response.setTradeId(exchange.getTradeId());
        response.setExchangeId(exchange.getExchangeId());
        response.setStatus(exchange.getStatus());
        response.setErrorCode(exchange.getErrorCode());
        return response;
    }

}
