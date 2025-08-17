package com.emlynma.ms.data.lock;

import com.emlynma.ms.core.base.BaseErrorCode;
import com.emlynma.ms.core.exception.SystemException;
import com.emlynma.ms.core.util.InvokeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LockImpl implements Lock {

    private final LockParams params;

    private final LockExecutor executor;

    private long lockTime = 0;


    @Override
    public void lock() {
        int effect = InvokeUtils.callQuietly(() -> executor.lock(params), 0);
        if (effect != 1) {
            log.error("lock fail, params={}", params);
            throw new SystemException(BaseErrorCode.DB_LOCK_ERROR);
        }
        lockTime = System.currentTimeMillis();
    }

    @Override
    public void unlock() {
        if (lockTime == 0L) {
            log.warn("no locked, params={}", params);
            return;
        }

        int effect = InvokeUtils.callQuietly(() -> executor.unlock(params), 0);
        if (effect != 1) {
            log.error("unlock fail, params={}", params);
            throw new SystemException(BaseErrorCode.DB_UNLOCK_ERROR);
        }
    }

}
