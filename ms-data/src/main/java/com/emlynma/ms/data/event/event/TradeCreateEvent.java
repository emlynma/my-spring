package com.emlynma.ms.data.event.event;

import com.emlynma.ms.data.domain.entity.Trade;
import lombok.Data;

@Data
public class TradeCreateEvent {

    private Trade record;

}
