package com.emlynma.ms.trade.contract.request;

import lombok.Data;

@Data
public class PrepayRequest {

    private String merchantId;
    private String outTradeId;

    private Long uid;

    private Long amount;
    private String currency;

    private String subject;
    private String attach;
    private String notifyUrl;

}
