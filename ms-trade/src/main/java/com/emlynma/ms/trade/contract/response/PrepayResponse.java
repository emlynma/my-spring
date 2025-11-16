package com.emlynma.ms.trade.contract.response;

import com.emlynma.ms.data.domain.enums.TradeStatusEnum;
import lombok.Data;

@Data
public class PrepayResponse {

    private String tradeId;
    private TradeStatusEnum status;

}
