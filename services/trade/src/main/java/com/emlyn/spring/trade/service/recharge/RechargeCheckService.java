package com.emlyn.spring.trade.service.recharge;

public interface RechargeCheckService {

    /**
     * 幂等校验(创建)
     */
    void checkCreateIdempotency(String outTradeId);

    /**
     * 幂等校验(状态)
     */
    void checkStatusIdempotency(String tradeId);

}
