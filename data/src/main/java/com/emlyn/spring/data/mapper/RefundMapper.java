package com.emlyn.spring.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.emlyn.spring.data.component.lock.LockExecutor;
import com.emlyn.spring.data.component.lock.LockSQL;
import com.emlyn.spring.data.domain.entity.Refund;
import org.apache.ibatis.annotations.Update;

public interface RefundMapper extends BaseMapper<Refund>, LockExecutor {

    @Override
    @Update(LockSQL.REFUND_LOCKED_SQL)
    int locked(String key, String val, int expire);

    @Override
    @Update(LockSQL.REFUND_UNLOCK_SQL)
    int unlock(String key, String val);

}
