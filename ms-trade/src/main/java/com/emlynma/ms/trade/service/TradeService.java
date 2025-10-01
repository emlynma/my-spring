package com.emlynma.ms.trade.service;

import com.emlynma.ms.data.domain.entity.Trade;
import com.emlynma.ms.data.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    public void saveTrade(Trade trade) {
        tradeRepository.insert(trade);
    }

}
