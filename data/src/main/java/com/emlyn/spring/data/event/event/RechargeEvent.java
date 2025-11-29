package com.emlyn.spring.data.event.event;

import com.emlyn.spring.data.domain.entity.Recharge;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RechargeEvent {

    private Recharge oldRecord;

    private Recharge newRecord;

}
