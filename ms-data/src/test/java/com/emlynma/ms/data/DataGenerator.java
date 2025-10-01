package com.emlynma.ms.data;

import com.emlynma.ms.core.component.id.IdGenerator;
import com.emlynma.ms.data.component.id.TradeIdGenerator;
import com.emlynma.ms.data.domain.entity.Trade;
import com.emlynma.ms.data.domain.enums.TradeTypeEnum;
import com.emlynma.ms.data.domain.model.TradeExtraInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataGenerator {

    private final IdGenerator idGenerator;

    private final TradeIdGenerator tradeIdGenerator;

    public Trade generateTrade() {
        Trade trade = new Trade();
        trade.setMerchantId("1001");
        trade.setOutTradeId("1001" + "_" + idGenerator.generateId().toString());
        trade.setTradeId(tradeIdGenerator.generate(TradeTypeEnum.TRADE));
        trade.setUid(1710121088L);
        trade.setAmount(100L);
        trade.setCurrency("CNY");
        trade.setTradeType(TradeTypeEnum.TRADE);
        trade.setExtraInfo(new TradeExtraInfo());
        trade.setSubject("测试商品");
        trade.setAttach("测试附加信息");
        trade.setNotifyUrl("http://test.com/notify");
        trade.setCreateTime(new java.util.Date());
        trade.setUpdateTime(new java.util.Date());
        trade.setExpireTime(new java.util.Date(System.currentTimeMillis() + 60 * 1000)); // 10分钟后过期
        trade.setVersion(0);
        return trade;
    }

}
