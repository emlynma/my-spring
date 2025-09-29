package com.emlynma.ms.data.event.listener;

import com.emlynma.ms.data.event.event.TradeCreateEvent;
import com.emlynma.ms.data.event.event.TradeUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TradeEventListener {

    @EventListener
    public void onTradeCreate(TradeCreateEvent event) {
        log.info("Trade created: record={}", event.getRecord());
        // Add your custom logic here
    }

    @EventListener
    public void onTradeUpdate(TradeUpdateEvent event) {
        log.info("Trade updated: oldRecord={}, newRecord={}", event.getOldRecord(), event.getNewRecord());
        // Add your custom logic here
    }

}
