package emlyn.ma.my.spring.boot.joke.controller;

import emlyn.ma.my.spring.boot.common.CommonResponse;
import emlyn.ma.my.spring.boot.common.util.SpringContextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/health")
    CommonResponse<String> healthCheck() {
        return CommonResponse.success("app is running, env=" + SpringContextUtils.getEnv());
    }

}
