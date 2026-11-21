package com.emlyn.spring.data.component.id;

import com.emlyn.spring.common.util.HashUtils;
import com.emlyn.spring.data.domain.enums.type.TradeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class DataIdGenerator {

    private final com.emlyn.spring.common.component.id.IdGenerator idGenerator;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private String generate(String scene, String sharding) {
        StringBuilder id = new StringBuilder();
        // scene 2 bit
        id.append(scene);
        // datetime 14 bit (yyyyMMddHHmmss)
        id.append(LocalDate.now().format(formatter));
        // unique id 13 bit
        String uniqueId = String.valueOf(idGenerator.generateId());
        id.append(uniqueId.substring(uniqueId.length() - 13));
        // sharding 3 bit
        id.append(sharding);
        return id.toString();
    }

    public String generateTradeIdWithOuterId(TradeType tradeType, String outerId) {
        String crc32 = HashUtils.crc32(outerId);
        return generate(String.valueOf(tradeType.getCode()), crc32.substring(crc32.length() - 1));
    }

    public String generateTradeIdWithInnerId(TradeType tradeType, String innerId) {
        return generate(String.valueOf(tradeType.getCode()), innerId.substring(innerId.length() - 1));
    }

    public String generateExchangeIdWithTradeId(String tradeId) {
        return generate("EX", tradeId.substring(tradeId.length() - 1));
    }

}
