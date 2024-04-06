package com.emlynma.spring.core.util;

import com.emlynma.spring.core.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RedisUtils {

    private final RedisTemplate<Object, Object> redisTemplate;

    public boolean set(String key, Object value) {
        return RetryUtils.execute(() -> {
            redisTemplate.opsForValue().set(key, value);
            return true;
        }, e -> {
            log.warn("{}", BaseErrorCode.REDIS_ERROR, e);
            return false;
        });
    }

    public Object get(String key) {
        return RetryUtils.execute(() -> redisTemplate.opsForValue().get(key), e -> {
            log.warn("{}", BaseErrorCode.REDIS_ERROR, e);
            return null;
        });
    }


}
