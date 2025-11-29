package com.emlyn.spring.trade.controller;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.trade.handler.RechargeHandler;
import com.emlyn.spring.trade.handler.request.RechargeRequest;
import com.emlyn.spring.trade.handler.response.RechargeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recharge")
public class RechargeController {

    private final RechargeHandler rechargeHandler;

    @PostMapping("/create")
    public ApiResponse<RechargeResponse> create(@RequestBody RechargeRequest request) {
        return ApiResponse.success(rechargeHandler.handle(request));
    }

}
