package com.emlynma.ms.data.event;

import com.emlynma.ms.data.domain.entity.Trade;
import com.emlynma.ms.data.event.event.TradeCreateEvent;
import com.emlynma.ms.data.event.event.TradeUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishTradeCreateEvent(Trade trade) {
        TradeCreateEvent event = new TradeCreateEvent();
        event.setRecord(trade);
        applicationEventPublisher.publishEvent(event);
    }

    public  void publishTradeUpdateEvent(Trade trade, Trade update) {
        TradeUpdateEvent event = new TradeUpdateEvent();
        event.setOldRecord(trade);
        event.setNewRecord(update);
        applicationEventPublisher.publishEvent(event);
    }

}
