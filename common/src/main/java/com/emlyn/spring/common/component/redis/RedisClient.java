package com.emlyn.spring.common.component.redis;

import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.util.RetryUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisClient {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<Object, Object> redisTemplate;

    private RedisScript<Boolean> frequencyScript;

    @PostConstruct
    private void loadScript() {
        frequencyScript = RedisScript.of(new ClassPathResource("lua/frequency.lua"), Boolean.class);
    }

    public boolean set(String key, Object value, long expire) {
        Duration expireDuration = Duration.ofSeconds(expire);
        return RetryUtils.execute(() -> {
            redisTemplate.opsForValue().set(key, value, expireDuration);
            return true;
        }, e -> {
            log.warn("{}", SysErrorCode.REDIS_SET_ERROR, e);
            return false;
        });
    }

    public Object get(String key) {
        return RetryUtils.execute(() -> redisTemplate.opsForValue().get(key),
                e -> {
            log.warn("{}", SysErrorCode.REDIS_GET_ERROR, e);
            return null;
        });
    }

    public boolean frequency(String key, int duration, int count) {
        return RetryUtils.execute(() -> stringRedisTemplate.execute(frequencyScript, List.of(key), String.valueOf(duration), String.valueOf(count)),
                e -> {
            log.warn("{}", SysErrorCode.REDIS_EXE_ERROR, e);
            return false;
        });
    }

}
