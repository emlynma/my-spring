package com.emlyn.spring.trade.client;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.common.contract.exchange.request.PostingRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeClient {

    private final ExchangeFeign exchangeFeign;

    public ExchangeResponse posting(PostingRequest request) {
        try {
            ApiResponse<ExchangeResponse> apiResponse = exchangeFeign.posting(request);
            return apiResponse.getData();
        } catch (Exception e) {
            return null;
        }
    }

}
