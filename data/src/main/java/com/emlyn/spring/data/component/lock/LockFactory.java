package com.emlyn.spring.data.component.lock;

import com.emlyn.spring.common.handler.lock.Lock;
import com.emlyn.spring.data.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LockFactory {

    private static final int DEFAULT_EXPIRE = 10 * 1000; // 10s

    private final PaymentMapper paymentMapper;
    private final RefundMapper refundMapper;
    private final RechargeMapper rechargeMapper;
    private final WithdrawMapper withdrawMapper;
    private final ExchangeMapper exchangeMapper;

    public Lock createPaymentLock(String tradeId) {
        return new LockImpl(paymentMapper, tradeId, UUID.randomUUID().toString(), DEFAULT_EXPIRE);
    }

    public Lock createRefundLock(String tradeId) {
        return new LockImpl(refundMapper, tradeId, UUID.randomUUID().toString(), DEFAULT_EXPIRE);
    }

    public Lock createRechargeLock(String tradeId) {
        return new LockImpl(rechargeMapper, tradeId, UUID.randomUUID().toString(), DEFAULT_EXPIRE);
    }

    public Lock createWithdrawLock(String tradeId) {
        return new LockImpl(withdrawMapper, tradeId, UUID.randomUUID().toString(), DEFAULT_EXPIRE);
    }

    public Lock createExchangeLock(String exchangeId) {
        return new LockImpl(exchangeMapper, exchangeId, UUID.randomUUID().toString(), DEFAULT_EXPIRE);
    }

}
