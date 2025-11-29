package com.emlyn.spring.data.repository;

import com.emlyn.spring.data.domain.entity.Recharge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RechargeRepositoryTest {

    @Autowired
    private RechargeRepository rechargeRepository;

    @Test
    public void testSelect() {
        Recharge condition = new Recharge();
        condition.setTradeId("20240612000000000001");
        condition.setOutTradeId("OUT123456789");
        List<Recharge> select = rechargeRepository.select(condition);
    }

}
