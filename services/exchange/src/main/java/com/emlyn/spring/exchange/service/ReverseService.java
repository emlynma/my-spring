package com.emlyn.spring.exchange.service;

import com.emlyn.spring.common.contract.exchange.request.ReverseRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import org.springframework.stereotype.Service;

@Service
public class ReverseService {

    public ExchangeResponse reverse(ReverseRequest request) {
        // mock implementation
        ExchangeResponse response = new ExchangeResponse();
        response.setExchangeId(request.getExchangeId());
        response.setStatus("SUCCESS");
        return response;
    }

}
