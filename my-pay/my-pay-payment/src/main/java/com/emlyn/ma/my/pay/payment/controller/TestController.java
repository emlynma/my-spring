package com.emlyn.ma.my.pay.payment.controller;

import com.emlyn.ma.my.pay.common.CommonResponse;
import com.emlyn.ma.my.pay.common.util.SpringContextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/health")
    public CommonResponse<String> health() {
        return CommonResponse.success("payment is running, env=" + SpringContextUtils.getEnv());
    }

}
