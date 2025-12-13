package com.emlyn.spring.data.component.id;

import com.emlyn.spring.common.component.id.IdGenerator;
import com.emlyn.spring.common.util.HashUtils;
import com.emlyn.spring.data.domain.enums.TradeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TradeIdGenerator {

    private final IdGenerator idGenerator;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * generate 32 bit unique tradeId
     */
    public String generate(String flag, String sharding) {
        StringBuilder tradeId = new StringBuilder();
        // flag 2 bit
        tradeId.append(flag);
        // date 8 bit (yyyyMMdd)
        tradeId.append(LocalDate.now().format(formatter));
        // fixed 5 bit
        tradeId.append("00000");
        // unique id 16 bit
        String uniqueId = String.format("%016d", idGenerator.generateId());
        tradeId.append(uniqueId.substring(uniqueId.length() - 16));
        // sharding 1 bit
        tradeId.append(sharding);
        return tradeId.toString();
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
