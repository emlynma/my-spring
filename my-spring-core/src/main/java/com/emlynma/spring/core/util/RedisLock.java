package com.emlynma.spring.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
public class RedisLock implements Lock {

    private static final ThreadLocal<String> LOCAL_LOCK_TOKEN = new ThreadLocal<>();
    private static final long SLEEP_TIME = 20;
    private static final long DEFAULT_EXPIRE_TIME = 1000 * 60;
    private static final String LOCK_PREFIX = "lock:";
    private static final String LOCK_LUA_SCRIPT = "if redis.call('set', KEYS[1], ARGV[1], 'nx', 'px', ARGV[2]) then return 1 else return 0 end";
    private static final String UNLOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private final StringRedisTemplate redisTemplate;

    private final String lockKey;
    private final long expireTime;

    public RedisLock(String lockKey) {
        this.lockKey = lockKey;
        this.expireTime = DEFAULT_EXPIRE_TIME;
        this.redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
    }

    public RedisLock(String lockKey, long expireTime, TimeUnit unit) {
        this.lockKey = lockKey;
        this.expireTime = unit.toMillis(expireTime);
        this.redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
    }

    @Override
    public boolean tryLock() {
        String redisKey = LOCK_PREFIX + lockKey;
        String redisValue = UUID.randomUUID().toString();
        Boolean result;
        try {
            RedisScript<Boolean> lockScript = RedisScript.of(LOCK_LUA_SCRIPT, Boolean.class);
            result = redisTemplate.execute(lockScript, List.of(redisKey), redisValue, String.valueOf(expireTime));
        } catch (Exception e) {
            throw new RuntimeException("tryLock error");
        }
        if (Boolean.TRUE.equals(result)) {
            LOCAL_LOCK_TOKEN.set(redisValue);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        long remainTime = unit.toMillis(time);
        long endTime = System.currentTimeMillis() + remainTime;
        while (remainTime > 0) {
            if (tryLock()) {
                return true;
            }
            try {
                Thread.sleep(Math.min(remainTime, SLEEP_TIME));
            } catch (InterruptedException e) {
                throw new RuntimeException("tryLock error");
            }
            remainTime = endTime - System.currentTimeMillis();
        }
        return false;
    }

    @Override
    public void lock() {
        while (true) {
            if (tryLock()) {
                return;
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException("lock error");
            }
        }
    }

    @Override
    public void unlock() {
        String redisKey = LOCK_PREFIX + lockKey;
        String redisValue = LOCAL_LOCK_TOKEN.get();
        Boolean result;
        try {
            RedisScript<Boolean> unlockScript = RedisScript.of(UNLOCK_LUA_SCRIPT, Boolean.class);
            result = redisTemplate.execute(unlockScript, List.of(redisKey), redisValue);
        } catch (Exception e) {
            throw new RuntimeException("unlock error");
        } finally {
            LOCAL_LOCK_TOKEN.remove();
        }
        if (Boolean.FALSE.equals(result)) {
            log.warn("unlock failed");
        }
    }

    @Override
    public void lockInterruptibly() {
        throw new UnsupportedOperationException();
    }

    @Override @NonNull
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

}
