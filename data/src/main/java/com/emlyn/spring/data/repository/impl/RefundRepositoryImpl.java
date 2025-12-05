package com.emlyn.spring.data.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.common.util.CopyUtils;
import com.emlyn.spring.data.domain.entity.Refund;
import com.emlyn.spring.data.event.TradeEventPublisher;
import com.emlyn.spring.data.mapper.RefundMapper;
import com.emlyn.spring.data.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepository {

    private final RefundMapper refundMapper;
    private final TradeEventPublisher tradeEventPublisher;

    private LambdaUpdateWrapper<Refund> buildUpdateWrapper(Refund condition) {
        Assert.isTrue(condition.getTradeId() != null, "sharding key(tradeId) must not be all null");
        return new LambdaUpdateWrapper<Refund>().eq(Refund::getTradeId, condition.getTradeId());
    }

    private LambdaQueryWrapper<Refund> buildQueryWrapper(Refund condition) {
        Assert.isTrue(condition.getTradeId() != null || condition.getOutTradeId() != null, "sharding key(tradeId or outTradeId) must not be all null");
        if (condition.getTradeId() != null) {
            return new LambdaQueryWrapper<Refund>().eq(Refund::getTradeId, condition.getTradeId());
        } else {
            return new LambdaQueryWrapper<Refund>().eq(Refund::getOutTradeId, condition.getOutTradeId());
        }
    }

    @Override
    public void insert(Refund entity) {
        int effect = refundMapper.insert(entity);
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_INSERT_ERROR);
        }
        Refund record = refundMapper.selectOne(buildQueryWrapper(entity));
        CopyUtils.copyNonNullProperties(record, entity, Refund.class);
        tradeEventPublisher.publishEvent(null, entity);
    }

    @Override
    public void delete(Refund entity) {
        int effect = refundMapper.delete(buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_DELETE_ERROR);
        }
    }

    @Override
    public void update(Refund entity, Refund update) {
        update.setVersion(entity.getVersion() + 1);
        int effect = refundMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_UPDATE_ERROR);
        }
        Refund backup = new Refund();
        CopyUtils.copyNonNullProperties(entity, backup, Refund.class);
        CopyUtils.copyNonNullProperties(update, entity, Refund.class);
        tradeEventPublisher.publishEvent(backup, entity);
    }

    @Override
    public List<Refund> select(Refund condition) {
        return refundMapper.selectList(buildQueryWrapper(condition));
    }

}
