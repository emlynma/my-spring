package com.emlyn.spring.trade;

import com.emlyn.spring.trade.domain.config.CommonConfig;
import com.emlyn.spring.trade.handler.RechargeHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeApplicationTest {

    @Autowired
    private RechargeHandler rechargeHandler;

    @Autowired
    private CommonConfig commonConfig;

    @Test
    public void contextLoads() {

        System.out.println("TradeApplicationTest contextLoads");

    }

}
