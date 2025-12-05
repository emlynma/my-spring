package com.emlyn.spring.data.component.lock;

public interface LockSQL {

    String PAYMENT_LOCKED_SQL = """
            update payment
            set lock_id = concat(#{val}, '_', floor(unix_timestamp(now(3)) * 1000) + #{expire})
            where trade_id = #{key}
            and (
                (lock_id is null) or
                (substring_index(lock_id, '_', 1) = #{val}) or
                (floor(unix_timestamp(now(3)) * 1000) > (substring_index(lock_id, '_', -1)))
            )""";

    String PAYMENT_UNLOCK_SQL = """
            update payment
            set lock_id = null
            where trade_id = #{key}
            and (
                (lock_id is not null) and
                (substring_index(lock_id, '_', 1) = #{val})
            )""";

    String REFUND_LOCKED_SQL = """
            update refund
            set lock_id = concat(#{val}, '_', floor(unix_timestamp(now(3)) * 1000) + #{expire})
            where trade_id = #{key}
            and (
                (lock_id is null) or
                (substring_index(lock_id, '_', 1) = #{val}) or
                (floor(unix_timestamp(now(3)) * 1000) > (substring_index(lock_id, '_', -1)))
            )""";

    String REFUND_UNLOCK_SQL = """
            update refund
            set lock_id = null
            where trade_id = #{key}
            and (
                (lock_id is not null) and
                (substring_index(lock_id, '_', 1) = #{val})
            )""";

    String RECHARGE_LOCKED_SQL = """
            update recharge
            set lock_id = concat(#{val}, '_', floor(unix_timestamp(now(3)) * 1000) + #{expire})
            where trade_id = #{key}
            and (
                (lock_id is null) or
                (substring_index(lock_id, '_', 1) = #{val}) or
                (floor(unix_timestamp(now(3)) * 1000) > (substring_index(lock_id, '_', -1)))
            )""";

    String RECHARGE_UNLOCK_SQL = """
            update recharge
            set lock_id = null
            where trade_id = #{key}
            and (
                (lock_id is not null) and
                (substring_index(lock_id, '_', 1) = #{val})
            )""";

    String WITHDRAW_LOCKED_SQL = """
            update withdraw
            set lock_id = concat(#{val}, '_', floor(unix_timestamp(now(3)) * 1000) + #{expire})
            where trade_id = #{key}
            and (
                (lock_id is null) or
                (substring_index(lock_id, '_', 1) = #{val}) or
                (floor(unix_timestamp(now(3)) * 1000) > (substring_index(lock_id, '_', -1)))
            )""";

    String WITHDRAW_UNLOCK_SQL = """
            update withdraw
            set lock_id = null
            where trade_id = #{key}
            and (
                (lock_id is not null) and
                (substring_index(lock_id, '_', 1) = #{val})
            )""";

    String EXCHANGE_LOCKED_SQL = """
            update exchange
            set lock_id = concat(#{val}, '_', floor(unix_timestamp(now(3)) * 1000) + #{expire})
            where exchange_id = #{key}
            and (
                (lock_id is null) or
                (substring_index(lock_id, '_', 1) = #{val}) or
                (floor(unix_timestamp(now(3)) * 1000) > (substring_index(lock_id, '_', -1)))
            )""";

    String EXCHANGE_UNLOCK_SQL = """
            update exchange
            set lock_id = null
            where exchange_id = #{key}
            and (
                (lock_id is not null) and
                (substring_index(lock_id, '_', 1) = #{val})
            )""";

}
