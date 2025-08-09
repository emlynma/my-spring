package com.emlynma.ms.core.component.lock;

import com.emlynma.ms.core.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Redis 分布式锁
 * 支持重入
 * 支持续期
 */
@Slf4j
public class ComplexRedisLock implements Lock {

    private static final ThreadLocal<String> LOCAL_LOCK_TOKEN = new ThreadLocal<>();
    private static final long SLEEP_TIME = 20; // 20ms
    private static final long DEFAULT_EXPIRE_TIME = 1000 * 10; // 10s
    private static final String LOCK_PREFIX = "lock:";

    private static final String LOCK_LUA_SCRIPT =
            "if redis.call('exists', KEYS[1]) == 0 then " +
                    "  redis.call('set', KEYS[1], ARGV[1], 'px', ARGV[2]) " +
                    "  redis.call('set', KEYS[1]..':count', 1) " +
                    "  return 1 " +
                    "elseif redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "  redis.call('incr', KEYS[1]..':count') " +
                    "  redis.call('pexpire', KEYS[1], ARGV[2]) " +
                    "  return 1 " +
                    "else " +
                    "  return 0 " +
                    "end";

    private static final String UNLOCK_LUA_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "  local count = redis.call('get', KEYS[1]..':count') " +
                    "  if count == '1' then " +
                    "    redis.call('del', KEYS[1]) " +
                    "    redis.call('del', KEYS[1]..':count') " +
                    "    return 1 " +
                    "  else " +
                    "    redis.call('decr', KEYS[1]..':count') " +
                    "    return 1 " +
                    "  end " +
                    "else " +
                    "  return 0 " +
                    "end";

    private static final String RENEW_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "  return redis.call('pexpire', KEYS[1], ARGV[2]) " +
                    "else " +
                    "  return 0 " +
                    "end";


    private final StringRedisTemplate redisTemplate;

    private final String lockKey;
    private final long expireTime;

    private volatile Thread renewThread;
    private volatile boolean running = true;

    public ComplexRedisLock(String lockKey) {
        this.lockKey = lockKey;
        this.expireTime = DEFAULT_EXPIRE_TIME;
        this.redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
    }

    public ComplexRedisLock(String lockKey, long expireTime, TimeUnit unit) {
        this.lockKey = lockKey;
        this.expireTime = unit.toMillis(expireTime);
        this.redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
    }

    @Override
    public boolean tryLock() {
        String redisKey = LOCK_PREFIX + lockKey;
        String redisValue = UUID.randomUUID().toString();
        Boolean result = redisTemplate.execute(
                RedisScript.of(LOCK_LUA_SCRIPT, Boolean.class),
                List.of(redisKey), redisValue, String.valueOf(expireTime)
        );
        if (Boolean.TRUE.equals(result)) {
            LOCAL_LOCK_TOKEN.set(redisValue);
            startRenewTask();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, @NonNull TimeUnit unit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        long remainTime = unit.toMillis(time);
        long endTime = System.currentTimeMillis() + remainTime;
        while (remainTime > 0) {
            if (tryLock()) {
                return true;
            }
            Thread.sleep(Math.min(remainTime, SLEEP_TIME));
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
                // noinspection BusyWait
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                // 忽略中断，但保留中断状态
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        while (true) {
            if (tryLock()) {
                return;
            }
            // noinspection BusyWait
            Thread.sleep(SLEEP_TIME);
        }
    }

    @Override
    public void unlock() {
        String redisKey = LOCK_PREFIX + lockKey;
        String redisValue = LOCAL_LOCK_TOKEN.get();
        if (redisValue != null) {
            RedisScript<Boolean> unlockScript = RedisScript.of(UNLOCK_LUA_SCRIPT, Boolean.class);
            Boolean result = redisTemplate.execute(unlockScript, List.of(redisKey), redisValue);
            if (Boolean.FALSE.equals(result)) {
                // 失败重试一次
                redisTemplate.execute(unlockScript, List.of(redisKey), redisValue);
            }
            LOCAL_LOCK_TOKEN.remove();
            stopRenewTask();
        }
    }

    @Override @NonNull
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    private void startRenewTask() {
        renewThread = new Thread(() -> {
            while (running && !Thread.currentThread().isInterrupted()) {
                String redisKey = LOCK_PREFIX + lockKey;
                try {
                    // noinspection BusyWait
                    Thread.sleep(expireTime / 3);
                } catch (InterruptedException e) {
                    // 忽略中断，但保留中断状态
                    Thread.currentThread().interrupt();
                }
                String redisValue = LOCAL_LOCK_TOKEN.get();
                if (redisValue != null) {
                    RedisScript<Boolean> renewScript = RedisScript.of(RENEW_SCRIPT, Boolean.class);
                    Boolean result = redisTemplate.execute(renewScript, List.of(redisKey), redisValue, String.valueOf(expireTime));
                    if (Boolean.FALSE.equals(result)) {
                        log.warn("renew failed, lock may be expired");
                        break;
                    }
                }
            }
        });
        renewThread.setDaemon(true);
        renewThread.start();
    }

    private void stopRenewTask() {
        running = false;
        if (renewThread != null) {
            renewThread.interrupt();
            renewThread = null;
        }
    }

}
