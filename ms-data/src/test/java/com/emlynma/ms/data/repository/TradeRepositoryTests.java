package com.emlynma.ms.data.repository;

import com.emlynma.ms.core.component.id.IdGenerator;
import com.emlynma.ms.data.domain.entity.TradeDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TradeRepositoryTests {

    @Autowired
    private TradeRepository userRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Test
    public void test() {
        TradeDO condition = new TradeDO();
        condition.setTradeId("123");
        TradeDO tradeDO = userRepository.selectOne(condition);
        System.out.println(tradeDO);
    }

}
