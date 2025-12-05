package com.emlyn.spring.data.repository;

import com.emlyn.spring.common.contract.ExtraInfo;
import com.emlyn.spring.data.component.id.TradeIdGenerator;
import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.domain.enums.TradeType;
import com.emlyn.spring.data.domain.enums.status.RechargeStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
public class RechargeRepositoryTest {

    @Autowired
    private RechargeRepository rechargeRepository;

    @Autowired
    private TradeIdGenerator tradeIdGenerator;

    @Test
    public void crud() {
        Recharge recharge = createRecharge();
        // insert
        rechargeRepository.insert(recharge);
        // select
        List<Recharge> rechargeList = rechargeRepository.select(recharge);
        Assertions.assertFalse(CollectionUtils.isEmpty(rechargeList));
        // update
        Recharge update = new Recharge();
        update.setStatus(RechargeStatus.SUCCESS);
        rechargeRepository.update(recharge, update);
        rechargeList = rechargeRepository.select(recharge);
        Assertions.assertEquals(RechargeStatus.SUCCESS, rechargeList.get(0).getStatus());
        // delete
        rechargeRepository.delete(recharge);
        rechargeList = rechargeRepository.select(recharge);
        Assertions.assertTrue(CollectionUtils.isEmpty(rechargeList));
    }

    private Recharge createRecharge() {
        Recharge recharge = new Recharge();
        recharge.setChannelId("test_channel");
        recharge.setOutTradeId("test_outer_id_123456");
        recharge.setTradeId(tradeIdGenerator.generateIdWithOuterId(TradeType.RECHARGE, "test_outer_id_123456"));
        recharge.setUid(123456789L);
        recharge.setAmount(100L);
        recharge.setCurrency("CNY");
        recharge.setTradeType(TradeType.RECHARGE);
        recharge.setStatus(RechargeStatus.INITIAL);
        recharge.setExtraInfo(new ExtraInfo());
        return recharge;
    }

}
