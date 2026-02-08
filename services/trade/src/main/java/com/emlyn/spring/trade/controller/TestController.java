package com.emlyn.spring.trade.controller;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.trade.domain.config.ApplicationConfig;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final ApplicationConfig applicationConfig;

    private final MeterRegistry meterRegistry;

    @RequestMapping("/config")
    public ApiResponse<ApplicationConfig> config() {
        return ApiResponse.success(applicationConfig);
    }

    @RequestMapping("/metric")
    public ApiResponse<String> metric() {
        Counter counter = meterRegistry.counter("test.metric.total");
        counter.increment();
        return ApiResponse.success(null);
    }

}
