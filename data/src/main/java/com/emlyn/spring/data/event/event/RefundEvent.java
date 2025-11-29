package com.emlyn.spring.data.event.event;

import com.emlyn.spring.data.domain.entity.Refund;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefundEvent {

    private Refund oldRecord;

    private Refund newRecord;

}
