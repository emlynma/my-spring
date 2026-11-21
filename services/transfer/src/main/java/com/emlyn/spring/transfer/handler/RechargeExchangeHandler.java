package com.emlyn.spring.transfer.handler;

import com.emlyn.spring.common.handler.AbstractBizHandler;
import com.emlyn.spring.common.handler.annotation.Handler;
import com.emlyn.spring.transfer.domain.model.ExchangeParams;
import com.emlyn.spring.transfer.domain.model.ExchangeResult;
import com.emlyn.spring.transfer.service.data.recharge.RechargeCheckService;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class RechargeExchangeHandler extends AbstractBizHandler<ExchangeParams, ExchangeResult> {

    private final RechargeCheckService rechargeCheckService;

    @Override
    protected ExchangeResult doHandle(ExchangeParams request) {

        rechargeCheckService.checkStatusIdempotency(request.getTradeId());

        return null;
    }

}
