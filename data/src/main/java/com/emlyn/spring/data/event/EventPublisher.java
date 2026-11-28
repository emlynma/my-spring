package com.emlyn.spring.data.event;

import com.emlyn.spring.data.domain.entity.*;
import com.emlyn.spring.data.event.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

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
