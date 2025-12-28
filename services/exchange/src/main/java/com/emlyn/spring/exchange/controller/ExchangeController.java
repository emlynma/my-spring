package com.emlyn.spring.exchange.controller;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.common.contract.exchange.request.PostingRequest;
import com.emlyn.spring.common.contract.exchange.request.ReverseRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import com.emlyn.spring.exchange.service.PollingService;
import com.emlyn.spring.exchange.service.PostingService;
import com.emlyn.spring.exchange.service.ReverseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final PostingService postingService;
    private final PollingService pollingService;
    private final ReverseService reverseService;

    @PostMapping("/posting")
    public ApiResponse<ExchangeResponse> posting(PostingRequest request) {
        return ApiResponse.success(postingService.posting(request));
    }

    @PostMapping("/polling")
    public ApiResponse<ExchangeResponse> polling(PostingRequest request) {
        return ApiResponse.success(pollingService.polling(request));
    }

    @PostMapping("/reverse")
    public ApiResponse<ExchangeResponse> reverse(ReverseRequest request) {
        return ApiResponse.success(reverseService.reverse(request));
    }

}
