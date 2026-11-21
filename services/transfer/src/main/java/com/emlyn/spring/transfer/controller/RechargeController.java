package com.emlyn.spring.transfer.controller;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.transfer.handler.RechargeHandler;
import com.emlyn.spring.transfer.handler.request.RechargeRequest;
import com.emlyn.spring.transfer.handler.response.RechargeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer/recharge")
public class RechargeController {

    private final RechargeHandler rechargeHandler;

    @PostMapping("/create")
    public ApiResponse<RechargeResponse> create(@Valid @RequestBody RechargeRequest request) {
        return ApiResponse.success(rechargeHandler.handle(request));
    }

}
