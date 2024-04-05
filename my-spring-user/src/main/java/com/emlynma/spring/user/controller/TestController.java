package com.emlynma.spring.user.controller;

import com.emlynma.spring.core.util.JsonUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public Object hello() {
        JsonUtils.toJson("");
        return "hello world";
    }

    @RequestMapping("/exception")
    public String exception() {
        throw new RuntimeException("test exception");
    }

}
