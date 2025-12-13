package com.emlyn.spring.trade.service.data.recharge;

import com.emlyn.spring.data.domain.entity.Recharge;

public interface RechargeQueryService {

    Recharge queryByTradeId(String tradeId);

    Recharge queryByOutTradeId(String outTradeId);

}
