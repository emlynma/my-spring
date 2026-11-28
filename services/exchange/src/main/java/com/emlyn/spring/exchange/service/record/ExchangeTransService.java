package com.emlyn.spring.exchange.service.record;

import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.data.domain.enums.status.ExchangeStatus;

public interface ExchangeTransService {

    /**
     * 创建单据
     */
    Exchange createExchange(Exchange exchange);

    /**
     * 更新单据信息，但不更新状态
     */
    void updateExchange(Exchange exchange, Exchange update);

    /**
     * 推进单据状态
     */
    void advanceExchangeStatus(Exchange exchange, Exchange update, ExchangeStatus status);

}
