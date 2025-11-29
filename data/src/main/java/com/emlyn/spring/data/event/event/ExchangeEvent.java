package com.emlyn.spring.data.event.event;

import com.emlyn.spring.data.domain.entity.Exchange;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeEvent {

    private Exchange oldRecord;

    private Exchange newRecord;

}
