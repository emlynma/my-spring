package com.emlyn.spring.trade.controller;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.trade.domain.config.CommonConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final CommonConfig commonConfig;

    @RequestMapping("/config")
    public ApiResponse<CommonConfig> test() {
        return ApiResponse.success(commonConfig);
    }

}
