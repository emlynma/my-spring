package com.emlyn.spring.exchange.service;

import com.emlyn.spring.common.contract.exchange.request.PostingRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import org.springframework.stereotype.Service;

@Service
public class PostingService {

    public ExchangeResponse posting(PostingRequest request) {
        // mock implementation
        ExchangeResponse response = new ExchangeResponse();
        response.setExchangeId(request.getExchangeId());
        response.setStatus("SUCCESS");
        return response;
    }

}
