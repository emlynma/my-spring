package com.emlyn.spring.exchange.service.record;

public interface ExchangeCheckService {

    /**
     * 幂等校验(创建)
     */
    void checkCreateIdempotency(String exchangeId);

    /**
     * 幂等校验(状态)
     */
    void checkStatusIdempotency(String exchangeId);

}
