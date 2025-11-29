package com.emlyn.spring.common.contract.exchange.request;

import com.emlyn.spring.common.contract.ExtraInfo;
import lombok.Data;

@Data
public class PollingRequest {

    private String exchangeId;

    private ExtraInfo extraInfo;

}
