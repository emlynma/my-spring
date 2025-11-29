package com.emlyn.spring.trade.handler.request;

import lombok.Data;

@Data
public class RechargeRequest {

    private Long uid;

    private String channelId;
    private String outTradeId;

    private Long amount;
    private String currency;

}
