package com.emlyn.spring.transfer.service;

import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.transfer.domain.model.ExchangeParams;
import com.emlyn.spring.transfer.domain.model.ExchangeResult;

public interface ExchangeService {

    ExchangeResult exchange(ExchangeParams request);

    void exchange(Exchange exchange);

}
