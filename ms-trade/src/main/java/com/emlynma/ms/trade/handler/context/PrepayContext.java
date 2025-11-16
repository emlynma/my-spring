package com.emlynma.ms.trade.handler.context;

import com.emlynma.ms.data.domain.entity.Trade;
import com.emlynma.ms.trade.contract.request.PrepayRequest;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class PrepayContext {

    private PrepayRequest request;

    private Trade trade;

}
