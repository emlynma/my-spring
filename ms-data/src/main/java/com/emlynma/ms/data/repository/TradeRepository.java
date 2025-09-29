package com.emlynma.ms.data.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlynma.ms.core.base.BaseErrorCode;
import com.emlynma.ms.core.exception.SystemException;
import com.emlynma.ms.core.util.CopyUtils;
import com.emlynma.ms.data.domain.entity.Trade;
import com.emlynma.ms.data.event.EventPublisher;
import com.emlynma.ms.data.mapper.TradeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TradeRepository {

    private final TradeMapper tradeMapper;

    private final EventPublisher eventPublisher;

    private LambdaUpdateWrapper<Trade> buildUpdateWrapper(Trade condition) {
        Assert.notNull(condition.getTradeId(), "sharding key(tradeId) must not be null");
        return new LambdaUpdateWrapper<Trade>()
                .eq(Objects.nonNull(condition.getId()), Trade::getId, condition.getId())
                .eq(Objects.nonNull(condition.getTradeId()), Trade::getTradeId, condition.getTradeId())
                ;
    }

    private LambdaQueryWrapper<Trade> buildQueryWrapper(Trade condition) {
        Assert.isTrue(condition.getTradeId() == null || condition.getOutTradeId() == null, "sharding key(tradeId or outTradeId) must not be null");
        return new LambdaQueryWrapper<Trade>()
                .eq(Objects.nonNull(condition.getId()), Trade::getId, condition.getId())
                .eq(Objects.nonNull(condition.getTradeId()), Trade::getTradeId, condition.getTradeId())
                .eq(Objects.nonNull(condition.getMerchantId()), Trade::getMerchantId, condition.getMerchantId())
                .eq(Objects.nonNull(condition.getOutTradeId()), Trade::getOutTradeId, condition.getOutTradeId())
                ;
    }

    public void insert(Trade entity) {
        int effect = tradeMapper.insert(entity);
        if (effect != 1) {
            throw new SystemException(BaseErrorCode.DB_INSERT_ERROR);
        }
        eventPublisher.publishTradeCreateEvent(entity);
    }

    public void delete(Trade entity) {
        int effect = tradeMapper.delete(buildQueryWrapper(entity));
        if (effect != 1) {
            throw new SystemException(BaseErrorCode.DB_DELETE_ERROR);
        }
    }

    public void update(Trade entity, Trade update) {
        int effect = tradeMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SystemException(BaseErrorCode.DB_UPDATE_ERROR);
        }
        Trade backup = new Trade();
        CopyUtils.copyNonNullProperties(entity, backup, Trade.class);
        CopyUtils.copyNonNullProperties(update, entity, Trade.class);
        eventPublisher.publishTradeUpdateEvent(backup, entity);
    }

    public Trade selectOne(Trade condition) {
        return tradeMapper.selectOne(buildQueryWrapper(condition));
    }

    public List<Trade> selectList(Trade condition) {
        return tradeMapper.selectList(buildQueryWrapper(condition));
    }

}
