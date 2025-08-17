package com.emlynma.ms.data.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlynma.ms.core.base.BaseErrorCode;
import com.emlynma.ms.core.exception.SystemException;
import com.emlynma.ms.core.util.CopyUtils;
import com.emlynma.ms.data.domain.entity.TradeDO;
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

    private LambdaUpdateWrapper<TradeDO> buildUpdateWrapper(TradeDO condition) {
        Assert.notNull(condition.getTradeId(), "sharding key(tradeId) must not be null");
        return new LambdaUpdateWrapper<TradeDO>()
                .eq(Objects.nonNull(condition.getId()), TradeDO::getId, condition.getId())
                .eq(Objects.nonNull(condition.getTradeId()), TradeDO::getTradeId, condition.getTradeId())
                ;
    }

    private LambdaQueryWrapper<TradeDO> buildQueryWrapper(TradeDO condition) {
        Assert.isTrue(condition.getTradeId() == null || condition.getOutTradeId() == null, "sharding key(tradeId or outTradeId) must not be null");
        return new LambdaQueryWrapper<TradeDO>()
                .eq(Objects.nonNull(condition.getId()), TradeDO::getId, condition.getId())
                .eq(Objects.nonNull(condition.getTradeId()), TradeDO::getTradeId, condition.getTradeId())
                .eq(Objects.nonNull(condition.getMerchantId()), TradeDO::getMerchantId, condition.getMerchantId())
                .eq(Objects.nonNull(condition.getOutTradeId()), TradeDO::getOutTradeId, condition.getOutTradeId())
                ;
    }

    public void insert(TradeDO entity) {
        int effect = tradeMapper.insert(entity);
        if (effect != 1) {
            throw new SystemException(BaseErrorCode.DB_INSERT_ERROR);
        }
    }

    public void delete(TradeDO entity) {
        int effect = tradeMapper.delete(buildQueryWrapper(entity));
        if (effect != 1) {
            throw new SystemException(BaseErrorCode.DB_DELETE_ERROR);
        }
    }

    public void update(TradeDO entity, TradeDO update) {
        int effect = tradeMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new SystemException(BaseErrorCode.DB_UPDATE_ERROR);
        }
        if (entity != update) {
            CopyUtils.copyNonNullProperties(update, entity, TradeDO.class);
        }
    }

    public TradeDO selectOne(TradeDO condition) {
        return tradeMapper.selectOne(buildQueryWrapper(condition));
    }

    public List<TradeDO> selectList(TradeDO condition) {
        return tradeMapper.selectList(buildQueryWrapper(condition));
    }

}
