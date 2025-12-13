package com.emlyn.spring.trade.service;

import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.trade.domain.model.ExchangeParams;
import com.emlyn.spring.trade.domain.model.ExchangeResult;

public interface ExchangeService {

    ExchangeResult exchange(ExchangeParams request);

    void exchange(Exchange exchange);

}
