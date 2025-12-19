package com.emlyn.spring.data.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.common.util.CopyUtils;
import com.emlyn.spring.data.domain.entity.Withdraw;
import com.emlyn.spring.data.event.TradeEventPublisher;
import com.emlyn.spring.data.mapper.WithdrawMapper;
import com.emlyn.spring.data.repository.WithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WithdrawRepositoryImpl implements WithdrawRepository {

    private final WithdrawMapper withdrawMapper;
    private final TradeEventPublisher tradeEventPublisher;

    private LambdaUpdateWrapper<Withdraw> buildUpdateWrapper(Withdraw condition) {
        Assert.isTrue(condition.getTradeId() != null, "sharding key(tradeId) must not be all null");
        return new LambdaUpdateWrapper<Withdraw>().eq(Withdraw::getTradeId, condition.getTradeId());
    }

    private LambdaQueryWrapper<Withdraw> buildQueryWrapper(Withdraw condition) {
        Assert.isTrue(condition.getTradeId() != null || condition.getOutTradeId() != null, "sharding key(tradeId or outTradeId) must not be all null");
        if (condition.getTradeId() != null) {
            return new LambdaQueryWrapper<Withdraw>().eq(Withdraw::getTradeId, condition.getTradeId());
        } else {
            return new LambdaQueryWrapper<Withdraw>().eq(Withdraw::getOutTradeId, condition.getOutTradeId());
        }
    }

    @Override
    public void insert(Withdraw entity) {
        int effect = withdrawMapper.insert(entity);
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_INSERT_ERROR);
        }
        Withdraw record = withdrawMapper.selectOne(buildQueryWrapper(entity));
        CopyUtils.copyNonNullProperties(record, entity, Withdraw.class);
        tradeEventPublisher.publishEvent(null, entity);
    }

    @Override
    public void delete(Withdraw entity) {
        int effect = withdrawMapper.delete(buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_DELETE_ERROR);
        }
    }

    @Override
    public void update(Withdraw entity, Withdraw update) {
        update.setVersion(entity.getVersion() + 1);
        int effect = withdrawMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_UPDATE_ERROR);
        }
        Withdraw backup = new Withdraw();
        CopyUtils.copyNonNullProperties(entity, backup, Withdraw.class);
        CopyUtils.copyNonNullProperties(update, entity, Withdraw.class);
        tradeEventPublisher.publishEvent(backup, entity);
    }

    @Override
    public List<Withdraw> select(Withdraw condition) {
        return withdrawMapper.selectList(buildQueryWrapper(condition));
    }

    @Override
    public Withdraw selectOne(Withdraw condition) {
        return withdrawMapper.selectOne(buildQueryWrapper(condition));
    }

}
