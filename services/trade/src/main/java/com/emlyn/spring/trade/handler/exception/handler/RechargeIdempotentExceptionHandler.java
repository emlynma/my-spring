package com.emlyn.spring.trade.handler.exception.handler;

import com.emlyn.spring.common.handler.annotation.ExceptionHandler;
import com.emlyn.spring.common.handler.exception.BizExceptionHandler;
import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.domain.enums.status.RechargeStatus;
import com.emlyn.spring.trade.handler.exception.IdempotentException;
import com.emlyn.spring.trade.handler.RechargeHandler;
import com.emlyn.spring.trade.handler.response.RechargeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExceptionHandler(RechargeHandler.class)
public class RechargeIdempotentExceptionHandler implements BizExceptionHandler<IdempotentException, RechargeResponse> {

    @Override
    public RechargeResponse handle(IdempotentException exception) {
        log.info("RechargeIdempotentExceptionHandler handling: {}", exception.getErrorCode());
        Recharge recharge = (Recharge) exception.getRecord();
        RechargeResponse response = new RechargeResponse();
        recharge.setStatus(RechargeStatus.REFUSED);
        return response;
    }

}
