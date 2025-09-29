package com.emlynma.ms.data.repository;

import com.emlynma.ms.core.component.id.IdGenerator;
import com.emlynma.ms.data.domain.entity.Trade;
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
        Trade condition = new Trade();
        condition.setTradeId("123000");
        Trade trade = userRepository.selectOne(condition);
        System.out.println(trade);
    }

}
