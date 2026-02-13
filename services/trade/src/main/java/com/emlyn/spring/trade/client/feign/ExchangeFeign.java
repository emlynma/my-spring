package com.emlyn.spring.trade.client.feign;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.common.contract.exchange.request.PostingRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("my-spring-exchange")
public interface ExchangeFeign {

    @PostMapping("/exchange/posting")
    ApiResponse<ExchangeResponse> posting(PostingRequest request);

}
