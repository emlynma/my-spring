package com.emlynma.spring.user.controller;

import com.emlynma.spring.core.ApiResponse;
import com.emlynma.spring.core.util.SpringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/health")
    public ApiResponse<?> health() {
        return ApiResponse.success(Map.of("status", "running", "env", SpringUtils.getEnv()));
    }

    @RequestMapping("/exception")
    public String exception() {
        throw new RuntimeException("test exception");
    }

}
