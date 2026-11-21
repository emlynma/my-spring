package com.emlyn.spring.transfer.service.data.recharge.impl;

import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.transfer.error.TransferErrorCode;
import com.emlyn.spring.transfer.handler.exception.IdempotentException;
import com.emlyn.spring.transfer.service.data.recharge.RechargeCheckService;
import com.emlyn.spring.transfer.service.data.recharge.RechargeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RechargeCheckServiceImpl implements RechargeCheckService {

    private final RechargeQueryService rechargeQueryService;

    @Override
    public void checkCreateIdempotency(String outTradeId) {
        Recharge recharge = rechargeQueryService.queryByOutTradeId(outTradeId);
        if (recharge != null) {
            throw new IdempotentException(TransferErrorCode.RECHARGE_IDEMPOTENT, recharge);
        }
    }

    @Override
    public void checkStatusIdempotency(String tradeId) {
        Recharge recharge = rechargeQueryService.queryByTradeId(tradeId);
        if (recharge.isCompleted()) {
            throw new IdempotentException(TransferErrorCode.RECHARGE_IDEMPOTENT, recharge);
        }
    }

}
