package com.emlynma.ms.data.component.id;

import com.emlynma.ms.core.component.id.IdGenerator;
import com.emlynma.ms.data.domain.enums.TradeTypeEnum;
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
    public String generate(TradeTypeEnum tradeType) {
        StringBuilder tradeId = new StringBuilder();
        // trade type 2 bit
        tradeId.append(tradeType.getCode());
        // date 8 bit (yyyyMMdd)
        tradeId.append(LocalDate.now().format(formatter));
        // fixed 6 bit
        tradeId.append("000000");
        // unique id 16 bit
        String uniqueId = String.format("%016d", idGenerator.generateId());
        tradeId.append(uniqueId.substring(uniqueId.length() - 16));
        return tradeId.toString();
    }

}
