package com.emlyn.spring.common.contract.exchange.request;

import com.emlyn.spring.common.contract.ExtraInfo;
import lombok.Data;

@Data
public class ReverseRequest {

    private String exchangeId;

    private String originExchangeId;

    private ExtraInfo extraInfo;

}
