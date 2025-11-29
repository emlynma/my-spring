package com.emlyn.spring.common.contract.exchange.request;

import com.emlyn.spring.common.contract.ExtraInfo;
import com.emlyn.spring.common.contract.exchange.model.AssetInfo;
import lombok.Data;

@Data
public class PostingRequest {

    private String exchangeId;

    private Long amount;
    private String currency;

    private AssetInfo payerInfo;

    private AssetInfo payeeInfo;

    private ExtraInfo extraInfo;

}
