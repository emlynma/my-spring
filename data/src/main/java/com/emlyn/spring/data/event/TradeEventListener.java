package com.emlyn.spring.data.event;

import com.emlyn.spring.common.util.JsonUtils;
import com.emlyn.spring.data.event.event.*;
import com.emlyn.spring.data.event.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TradeEventListener {

    @EventListener
    public void onPaymentEvent(PaymentEvent event) {
        log.info("Received Payment Event: {}", JsonUtils.toJson(event));
    }

    @EventListener
    public void onRefundEvent(RefundEvent event) {
        log.info("Received Refund Event: {}", JsonUtils.toJson(event));
    }

    @EventListener
    public void onRechargeEvent(RechargeEvent event) {
        log.info("Received Recharge Event: {}", JsonUtils.toJson(event));
    }

    @EventListener
    public void onWithdrawEvent(WithdrawEvent event) {
        log.info("Received Withdraw Event: {}", JsonUtils.toJson(event));
    }

    @EventListener
    public void onExchangeEvent(ExchangeEvent event) {
        log.info("Received Exchange Event: {}", JsonUtils.toJson(event));
    }

}
