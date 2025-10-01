package com.emlynma.ms.data.repository;

import com.emlynma.ms.data.DataGenerator;
import com.emlynma.ms.data.domain.entity.Trade;
import com.emlynma.ms.data.domain.enums.TradeBizStatusEnum;
import com.emlynma.ms.data.domain.enums.TradeStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private DataGenerator dataGenerator;

    private Trade testTrade;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        testTrade = dataGenerator.generateTrade();
    }

    @Test
    void testInsert() {
        // 执行插入
        assertDoesNotThrow(() -> tradeRepository.insert(testTrade));
    }

    @Test
    void testSelectOne_ByTradeId() {
        // 先插入数据
        tradeRepository.insert(testTrade);
        
        // 根据tradeId查询
        Trade condition = new Trade();
        condition.setTradeId(testTrade.getTradeId());
        Trade result = tradeRepository.selectOne(condition);
        
        // 验证查询结果
        assertNotNull(result);
    }

    @Test
    void testSelectOne_ByOutTradeId() {
        // 先插入数据
        tradeRepository.insert(testTrade);
        System.out.println("插入数据: outTradeId=" + testTrade.getOutTradeId() + ", merchantId=" + testTrade.getMerchantId());
        // 根据outTradeId查询
        Trade condition = new Trade();
        condition.setOutTradeId(testTrade.getOutTradeId());
        condition.setMerchantId(testTrade.getMerchantId());
        Trade result = tradeRepository.selectOne(condition);

        // 验证查询结果
        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        // 先插入数据
        tradeRepository.insert(testTrade);
        
        // 准备更新数据
        Trade update = new Trade();
        update.setStatus(TradeStatusEnum.SUCCESS);
        update.setBizStatus(TradeBizStatusEnum.SUCCESS);
        update.setPaymentId("PAY_" + System.currentTimeMillis());
        update.setFinishTime(new Date());
        update.setVersion(testTrade.getVersion() + 1);
        
        // 执行更新
        assertDoesNotThrow(() -> tradeRepository.update(testTrade, update));
        
        // 验证更新成功
        Trade condition = new Trade();
        condition.setTradeId(testTrade.getTradeId());
        Trade result = tradeRepository.selectOne(condition);
        
        assertNotNull(result);
        assertEquals(TradeStatusEnum.SUCCESS, result.getStatus());
        assertEquals(TradeBizStatusEnum.SUCCESS, result.getBizStatus());
        assertNotNull(result.getPaymentId());
        assertNotNull(result.getFinishTime());
    }

    @Test
    void testDelete() {
        // 先插入数据
        tradeRepository.insert(testTrade);
        
        // 验证数据存在
        Trade condition = new Trade();
        condition.setTradeId(testTrade.getTradeId());
        Trade beforeDelete = tradeRepository.selectOne(condition);
        assertNotNull(beforeDelete);
        
        // 执行删除
        assertDoesNotThrow(() -> tradeRepository.delete(condition));
        
        // 验证删除成功
        Trade afterDelete = tradeRepository.selectOne(condition);
        assertNull(afterDelete);
    }

    @Test
    void testSelectOne_NotFound() {
        // 查询不存在的tradeId
        Trade condition = new Trade();
        condition.setTradeId("1234567890");
        
        Trade result = tradeRepository.selectOne(condition);
        
        // 验证返回null
        assertNull(result);
    }

    @Test
    void testSelectList_EmptyResult() {
        // 查询不存在的merchantId
        Trade condition = new Trade();
        condition.setTradeId("1234567890");
        
        List<Trade> results = tradeRepository.selectList(condition);
        
        // 验证返回空列表
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void testUpdate_WithError() {
        // 先插入数据
        tradeRepository.insert(testTrade);
        
        // 准备更新数据，设置错误信息
        Trade updateData = new Trade();
        updateData.setStatus(TradeStatusEnum.FAIL);
        updateData.setBizStatus(TradeBizStatusEnum.FAIL);
        updateData.setErrorCode("PAYMENT_FAILED");
        updateData.setErrorDesc("支付失败");
        updateData.setVersion(testTrade.getVersion() + 1);
        
        // 执行更新
        assertDoesNotThrow(() -> tradeRepository.update(testTrade, updateData));
        
        // 验证更新成功
        Trade condition = new Trade();
        condition.setTradeId(testTrade.getTradeId());
        Trade result = tradeRepository.selectOne(condition);
        
        assertNotNull(result);
        assertEquals(TradeStatusEnum.FAIL, result.getStatus());
        assertEquals(TradeBizStatusEnum.FAIL, result.getBizStatus());
        assertEquals("PAYMENT_FAILED", result.getErrorCode());
        assertEquals("支付失败", result.getErrorDesc());
    }

    @Test
    void testInsert_DuplicateTradeId() {
        // 先插入一条数据
        tradeRepository.insert(testTrade);
        
        // 尝试插入相同tradeId的数据
        Trade duplicateTrade = dataGenerator.generateTrade();
        duplicateTrade.setTradeId(testTrade.getTradeId()); // 相同的tradeId
        
        // 验证插入失败（应该抛出异常）
        assertThrows(Exception.class, () -> tradeRepository.insert(duplicateTrade));
    }

}
