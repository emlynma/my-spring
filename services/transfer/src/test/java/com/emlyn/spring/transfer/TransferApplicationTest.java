package com.emlyn.spring.transfer;

import com.emlyn.spring.common.util.JsonUtils;
import com.emlyn.spring.transfer.domain.config.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransferApplicationTest {


    @Autowired
    private ApplicationConfig applicationConfig;

    @Test
    public void contextLoads() {
        System.out.println("TransferApplicationTest contextLoads");
    }

    @Test
    public void configLoads() {
        System.out.println(JsonUtils.toJson(applicationConfig));
    }

}
