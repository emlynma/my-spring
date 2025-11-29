package com.emlyn.spring.data.event.event;

import com.emlyn.spring.data.domain.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentEvent {

    private Payment oldRecord;

    private Payment newRecord;

}
