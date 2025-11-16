package com.emlynma.ms.trade.controller;

import com.emlynma.ms.core.base.ApiResponse;
import com.emlynma.ms.trade.contract.request.PrepayRequest;
import com.emlynma.ms.trade.contract.response.PrepayResponse;
import com.emlynma.ms.trade.handler.PrepayHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final PrepayHandler prepayHandler;

    @PostMapping("/prepay")
    public ApiResponse<PrepayResponse> prepay(PrepayRequest request) {
        var response = prepayHandler.handle(request);
        return ApiResponse.success(response);
    }

}
