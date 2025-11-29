package com.emlyn.spring.trade.service.recharge;

import com.emlyn.spring.data.domain.entity.Recharge;

public interface RechargeQueryService {

    Recharge queryByTradeId(String tradeId);

    Recharge queryByOutTradeId(String outTradeId);

}
