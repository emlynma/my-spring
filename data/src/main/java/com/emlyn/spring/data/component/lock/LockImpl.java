package com.emlyn.spring.data.component.lock;

import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.exception.SysException;
import com.emlyn.spring.common.handler.lock.Lock;
import com.emlyn.spring.common.util.InvokeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LockImpl implements Lock {

    private final LockExecutor executor;

    private final String key;
    private final String val;
    private final int expire;

    private long lockedTime = 0;

    @Override
    public void locked() {
        int effect = InvokeUtils.callQuietly(() -> executor.locked(key, val, expire), 0);
        if (effect != 1) {
            log.error("lock fail, key={}, val={}, expire={}", key, val, expire);
            throw new SysException(SysErrorCode.DB_LOCKED_ERROR);
        }
        lockedTime = System.currentTimeMillis();
    }

    @Override
    public void unlock() {
        if (lockedTime == 0L) {
            return;
        }
        int effect = InvokeUtils.callQuietly(() -> executor.unlock(key, val), 0);
        if (effect != 1) {
            log.error("unlock fail, key={}, val={}", key, val);
            throw new SysException(SysErrorCode.DB_UNLOCK_ERROR);
        }
    }

}
