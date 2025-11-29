package com.emlyn.spring.data.event.event;

import com.emlyn.spring.data.domain.entity.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WithdrawEvent {

    private Withdraw oldRecord;

    private Withdraw newRecord;

}
