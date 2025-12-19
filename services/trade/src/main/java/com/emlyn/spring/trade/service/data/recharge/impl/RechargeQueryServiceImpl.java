package com.emlyn.spring.trade.service.data.recharge.impl;

import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.repository.RechargeRepository;
import com.emlyn.spring.trade.service.data.recharge.RechargeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RechargeQueryServiceImpl implements RechargeQueryService {

    private final RechargeRepository rechargeRepository;

    @Override
    public Recharge queryByTradeId(String tradeId) {
        Recharge condition = new Recharge();
        condition.setTradeId(tradeId);
        return rechargeRepository.selectOne(condition);
    }

    @Override
    public Recharge queryByOutTradeId(String outTradeId) {
        Recharge condition = new Recharge();
        condition.setOutTradeId(outTradeId);
        return rechargeRepository.selectOne(condition);
    }


}
