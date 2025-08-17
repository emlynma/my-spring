package com.emlynma.ms.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.emlynma.ms.data.domain.entity.TradeDO;
import com.emlynma.ms.data.lock.LockExecutor;
import com.emlynma.ms.data.lock.LockParams;
import org.apache.ibatis.annotations.Update;

public interface TradeMapper extends BaseMapper<TradeDO>, LockExecutor {

    @Update("""
            update trade
            set lock_id = concat(#{value}, '_', unix_timestamp(now(3)) * 1000 + #{expire})
            where trade_id = #{key}
            and (
                (lock_id is null) or
                (lock_id = '') or
                (substring_index(lock_id, '_', 1) = #{value}) or
                (unix_timestamp(now(3)) * 1000 > (substring_index(lock_id, '_', -1)))
            )""")
    @Override
    int lock(LockParams params);

    @Update("""
            update trade
            set lock_id = ''
            where trade_id = #{key}
            and (
                (lock_id is not null) and
                (lock_id != '') and
                (substring_index(lock_id, '_', 1) = #{value})
            )""")
    @Override
    int unlock(LockParams params);

}
