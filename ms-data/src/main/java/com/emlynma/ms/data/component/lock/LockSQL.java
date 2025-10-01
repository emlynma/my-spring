package com.emlynma.ms.data.component.lock;

public interface LockSQL {

    String TRADE_LOCK_SQL = """
            update trade
            set lock_id = concat(#{value}, '_', unix_timestamp(now(3)) * 1000 + #{expire})
            where trade_id = #{key}
            and (
                (lock_id is null) or
                (substring_index(lock_id, '_', 1) = #{value}) or
                (unix_timestamp(now(3)) * 1000 > (substring_index(lock_id, '_', -1)))
            )""";

    String TRADE_UNLOCK_SQL = """
            update trade
            set lock_id = null
            where trade_id = #{key}
            and (
                (lock_id is not null) and
                (substring_index(lock_id, '_', 1) = #{value})
            )""";
}
