package com.emlyn.spring.trade.controller;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.trade.domain.config.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final ApplicationConfig applicationConfig;

    @RequestMapping("/config")
    public ApiResponse<ApplicationConfig> test() {
        return ApiResponse.success(applicationConfig);
    }

}
