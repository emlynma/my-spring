package com.emlyn.spring.data.repository;

import com.emlyn.spring.common.contract.ExtraInfo;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.data.component.id.TradeIdGenerator;
import com.emlyn.spring.common.handler.lock.Lock;
import com.emlyn.spring.data.component.lock.LockFactory;
import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.domain.enums.TradeType;
import com.emlyn.spring.data.domain.enums.status.RechargeStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
public class RechargeRepositoryTest {

    @Autowired
    private RechargeRepository rechargeRepository;

    @Autowired
    private TradeIdGenerator tradeIdGenerator;

    @Autowired
    private LockFactory lockFactory;

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

    @Test
    @Transactional
    @Rollback
    public void testLocked() {
        Recharge recharge = createRecharge();

        rechargeRepository.insert(recharge);

        Lock lock = lockFactory.createRechargeLock(recharge.getTradeId());

        lock.locked();

        Assertions.assertThrows(SysException.class, () -> lockFactory.createRechargeLock(recharge.getTradeId()).locked());
        Assertions.assertDoesNotThrow(lock::locked);

        lock.unlock();

        Assertions.assertDoesNotThrow(() -> lockFactory.createRechargeLock(recharge.getTradeId()).locked());
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
