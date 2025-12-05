package com.emlyn.spring.data.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.common.util.CopyUtils;
import com.emlyn.spring.data.domain.entity.Payment;
import com.emlyn.spring.data.event.TradeEventPublisher;
import com.emlyn.spring.data.mapper.PaymentMapper;
import com.emlyn.spring.data.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentMapper paymentMapper;
    private final TradeEventPublisher tradeEventPublisher;

    private LambdaUpdateWrapper<Payment> buildUpdateWrapper(Payment condition) {
        Assert.isTrue(condition.getTradeId() != null, "sharding key(tradeId) must not be all null");
        return new LambdaUpdateWrapper<Payment>().eq(Payment::getTradeId, condition.getTradeId());
    }

    private LambdaQueryWrapper<Payment> buildQueryWrapper(Payment condition) {
        Assert.isTrue(condition.getTradeId() != null || condition.getOutTradeId() != null, "sharding key(tradeId or outTradeId) must not be all null");
        if (condition.getTradeId() != null) {
            return new LambdaQueryWrapper<Payment>().eq(Payment::getTradeId, condition.getTradeId());
        } else {
            return new LambdaQueryWrapper<Payment>().eq(Payment::getOutTradeId, condition.getOutTradeId());
        }
    }

    @Override
    public void insert(Payment entity) {
        int effect = paymentMapper.insert(entity);
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_INSERT_ERROR);
        }
        Payment record = paymentMapper.selectOne(buildQueryWrapper(entity));
        CopyUtils.copyNonNullProperties(record, entity, Payment.class);
        tradeEventPublisher.publishEvent(null, entity);
    }

    @Override
    public void delete(Payment entity) {
        int effect = paymentMapper.delete(buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_DELETE_ERROR);
        }
    }

    @Override
    public void update(Payment entity, Payment update) {
        update.setVersion(entity.getVersion() + 1);
        int effect = paymentMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_UPDATE_ERROR);
        }
        Payment backup = new Payment();
        CopyUtils.copyNonNullProperties(entity, backup, Payment.class);
        CopyUtils.copyNonNullProperties(update, entity, Payment.class);
        tradeEventPublisher.publishEvent(backup, entity);
    }

    @Override
    public List<Payment> select(Payment condition) {
        return paymentMapper.selectList(buildQueryWrapper(condition));
    }

}
