package com.emlyn.spring.trade;

import com.emlyn.spring.common.util.JsonUtils;
import com.emlyn.spring.trade.domain.config.ApplicationConfig;
import com.emlyn.spring.trade.handler.RechargeHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeApplicationTest {

    @Autowired
    private RechargeHandler rechargeHandler;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Test
    public void contextLoads() {
        System.out.println("TradeApplicationTest contextLoads");
    }

    @Test
    public void configLoads() {
        System.out.println(JsonUtils.toJson(applicationConfig));
    }

}
