package com.emlyn.spring.data.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.common.util.CopyUtils;
import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.data.event.TradeEventPublisher;
import com.emlyn.spring.data.mapper.ExchangeMapper;
import com.emlyn.spring.data.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExchangeRepositoryImpl implements ExchangeRepository {

    private final ExchangeMapper exchangeMapper;
    private final TradeEventPublisher tradeEventPublisher;

    private LambdaUpdateWrapper<Exchange> buildUpdateWrapper(Exchange condition) {
        Assert.isTrue(condition.getTradeId() != null, "sharding key(tradeId) must not be all null");
        return new LambdaUpdateWrapper<Exchange>().eq(Exchange::getTradeId, condition.getTradeId());
    }

    private LambdaQueryWrapper<Exchange> buildQueryWrapper(Exchange condition) {
        Assert.isTrue(condition.getTradeId() != null || condition.getExchangeId() != null, "sharding key(tradeId or exchangeId) must not be all null");
        if (condition.getTradeId() != null) {
            return new LambdaQueryWrapper<Exchange>().eq(Exchange::getTradeId, condition.getTradeId());
        } else {
            return new LambdaQueryWrapper<Exchange>().eq(Exchange::getExchangeId, condition.getExchangeId());
        }
    }

    @Override
    public void insert(Exchange entity) {
        int effect = exchangeMapper.insert(entity);
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_INSERT_ERROR);
        }
        Exchange record = exchangeMapper.selectOne(buildQueryWrapper(entity));
        CopyUtils.copyNonNullProperties(record, entity, Exchange.class);
        tradeEventPublisher.publishEvent(null, entity);
    }

    @Override
    public void delete(Exchange entity) {
        int effect = exchangeMapper.delete(buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_DELETE_ERROR);
        }
    }

    @Override
    public void update(Exchange entity, Exchange update) {
        update.setVersion(entity.getVersion() + 1);
        int effect = exchangeMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SysException(SysErrorCode.DB_UPDATE_ERROR);
        }
        Exchange backup = new Exchange();
        CopyUtils.copyNonNullProperties(entity, backup, Exchange.class);
        CopyUtils.copyNonNullProperties(update, entity, Exchange.class);
        tradeEventPublisher.publishEvent(backup, entity);
    }

    @Override
    public List<Exchange> select(Exchange condition) {
        return exchangeMapper.selectList(buildQueryWrapper(condition));
    }

    @Override
    public Exchange selectOne(Exchange condition) {
        return exchangeMapper.selectOne(buildQueryWrapper(condition));
    }

}
