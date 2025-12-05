package com.emlyn.spring.data.event;

import com.emlyn.spring.data.domain.entity.*;
import com.emlyn.spring.data.event.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(Payment oldRecord, Payment newRecord) {
        applicationEventPublisher.publishEvent(new PaymentEvent(oldRecord, newRecord));
    }

    public void publishEvent(Refund oldRecord, Refund newRecord) {
        applicationEventPublisher.publishEvent(new RefundEvent(oldRecord, newRecord));
    }

    public void publishEvent(Recharge oldRecord, Recharge newRecord) {
        applicationEventPublisher.publishEvent(new RechargeEvent(oldRecord, newRecord));
    }

    public void publishEvent(Withdraw oldRecord, Withdraw newRecord) {
        applicationEventPublisher.publishEvent(new WithdrawEvent(oldRecord, newRecord));
    }

    public void publishEvent(Exchange oldRecord, Exchange newRecord) {
        applicationEventPublisher.publishEvent(new ExchangeEvent(oldRecord, newRecord));
    }

}
