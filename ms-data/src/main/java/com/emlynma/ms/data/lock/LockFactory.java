package com.emlynma.ms.data.lock;

import com.emlynma.ms.data.mapper.TradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LockFactory {

    private static final long DEFAULT_EXPIRE = 10000L;

    private final TradeMapper tradeMapper;

    public Lock createTradeLock(String tradeId) {
        var params = new LockParams(tradeId, UUID.randomUUID().toString(), DEFAULT_EXPIRE);
        return new LockImpl(params, tradeMapper);
    }

}
