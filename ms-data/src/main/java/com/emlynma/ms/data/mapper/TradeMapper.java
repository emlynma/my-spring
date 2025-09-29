package com.emlynma.ms.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.emlynma.ms.data.domain.entity.Trade;
import com.emlynma.ms.data.lock.LockExecutor;
import com.emlynma.ms.data.lock.LockParams;
import com.emlynma.ms.data.lock.LockSQL;
import org.apache.ibatis.annotations.Update;

public interface TradeMapper extends BaseMapper<Trade>, LockExecutor {

    @Update(LockSQL.TRADE_LOCK_SQL)
    @Override
    int lock(LockParams params);

    @Update(LockSQL.TRADE_UNLOCK_SQL)
    @Override
    int unlock(LockParams params);

}
