package com.emlynma.spring.user.controller;

import com.emlynma.spring.core.ApiResponse;
import com.emlynma.spring.core.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
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

    @RequestMapping("/log")
    public String log() {
        log.debug("this is an debug log");
        log.info("this is an info log");
        log.warn("this is an warn log");
        log.error("this is an error log");
        return "ok";
    }

}
