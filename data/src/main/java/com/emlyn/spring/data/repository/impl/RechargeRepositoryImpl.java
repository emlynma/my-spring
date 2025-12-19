package com.emlyn.spring.data.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.common.util.CopyUtils;
import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.repository.RechargeRepository;
import com.emlyn.spring.data.event.TradeEventPublisher;
import com.emlyn.spring.data.mapper.RechargeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RechargeRepositoryImpl implements RechargeRepository {

    private final RechargeMapper rechargeMapper;
    private final TradeEventPublisher tradeEventPublisher;

    private LambdaUpdateWrapper<Recharge> buildUpdateWrapper(Recharge condition) {
        Assert.isTrue(condition.getTradeId() != null, "sharding key(tradeId) must not be all null");
        return new LambdaUpdateWrapper<Recharge>().eq(Recharge::getTradeId, condition.getTradeId());
    }

    private LambdaQueryWrapper<Recharge> buildQueryWrapper(Recharge condition) {
        Assert.isTrue(condition.getTradeId() != null || condition.getOutTradeId() != null, "sharding key(tradeId or outTradeId) must not be all null");
        if (condition.getTradeId() != null) {
            return new LambdaQueryWrapper<Recharge>().eq(Recharge::getTradeId, condition.getTradeId());
        } else {
            return new LambdaQueryWrapper<Recharge>().eq(Recharge::getOutTradeId, condition.getOutTradeId());
        }
    }

    @Override
    public void insert(Recharge entity) {
        int effect = rechargeMapper.insert(entity);
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_INSERT_ERROR);
        }
        Recharge record = rechargeMapper.selectOne(buildQueryWrapper(entity));
        CopyUtils.copyNonNullProperties(record, entity, Recharge.class);
        tradeEventPublisher.publishEvent(null, entity);
    }

    @Override
    public void delete(Recharge entity) {
        int effect = rechargeMapper.delete(buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_DELETE_ERROR);
        }
    }

    @Override
    public void update(Recharge entity, Recharge update) {
        update.setVersion(entity.getVersion() + 1);
        int effect = rechargeMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_UPDATE_ERROR);
        }
        Recharge backup = new Recharge();
        CopyUtils.copyNonNullProperties(entity, backup, Recharge.class);
        CopyUtils.copyNonNullProperties(update, entity, Recharge.class);
        tradeEventPublisher.publishEvent(backup, entity);
    }

    @Override
    public List<Recharge> select(Recharge condition) {
        return rechargeMapper.selectList(buildQueryWrapper(condition));
    }

    @Override
    public Recharge selectOne(Recharge condition) {
        return rechargeMapper.selectOne(buildQueryWrapper(condition));
    }

}
