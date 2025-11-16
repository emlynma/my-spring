package com.emlynma.ms.trade.handler;

import com.emlynma.ms.data.component.id.TradeIdGenerator;
import com.emlynma.ms.data.domain.entity.Trade;
import com.emlynma.ms.data.domain.enums.TradeBizStatusEnum;
import com.emlynma.ms.data.domain.enums.TradeStatusEnum;
import com.emlynma.ms.data.domain.enums.TradeTypeEnum;
import com.emlynma.ms.trade.contract.request.PrepayRequest;
import com.emlynma.ms.trade.contract.response.PrepayResponse;
import com.emlynma.ms.trade.handler.context.PrepayContext;
import com.emlynma.ms.trade.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrepayHandler {

    private final PrepayContext context;

    private final TradeService tradeService;

    private final TradeIdGenerator tradeIdGenerator;

    public PrepayResponse handle(PrepayRequest request) {
        // 1. 上下文
        buildContext(request);

        // 2. 幂等校验
        idempotencyCheck(request);

        // 3. 创建单据
        createTrade(request);

        // 4. 返回结果
        return buildResponse();
    }

    private void buildContext(PrepayRequest request) {
        context.setRequest(request);
    }

    private void idempotencyCheck(PrepayRequest request) {
        // Implement idempotency check logic here
    }

    private void createTrade(PrepayRequest request) {
        Trade trade = buildTrade(request);
        tradeService.saveTrade(trade);
        context.setTrade(trade);
    }

    private PrepayResponse buildResponse() {
        PrepayResponse response = new PrepayResponse();
        Trade trade = context.getTrade();
        response.setTradeId(trade.getTradeId());
        response.setStatus(trade.getStatus());
        return response;
    }

    private Trade buildTrade(PrepayRequest request) {
        Trade trade = new Trade();
        trade.setMerchantId(request.getMerchantId());
        trade.setOutTradeId(request.getOutTradeId());
        trade.setUid(request.getUid());
        trade.setTradeId(tradeIdGenerator.generate(TradeTypeEnum.TRADE));
        trade.setAmount(request.getAmount());
        trade.setCurrency(request.getCurrency());
        trade.setTradeType(TradeTypeEnum.TRADE);
        trade.setStatus(TradeStatusEnum.INIT);
        trade.setBizStatus(TradeBizStatusEnum.INIT);
        trade.setSubject(request.getSubject());
        trade.setAttach(request.getAttach());
        trade.setNotifyUrl(request.getNotifyUrl());
        return trade;
    }

}
