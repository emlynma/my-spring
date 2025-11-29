package com.emlyn.spring.trade.service.recharge.impl;

import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.repository.RechargeRepository;
import com.emlyn.spring.trade.service.recharge.RechargeQueryService;
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
        List<Recharge> rechargeList = rechargeRepository.select(condition);
        Assert.isTrue(rechargeList.size() == 1, "Recharge queryByTradeId not unique, tradeId: " + tradeId);
        return rechargeList.get(0);
    }

    @Override
    public Recharge queryByOutTradeId(String outTradeId) {
        Recharge condition = new Recharge();
        condition.setOutTradeId(outTradeId);
        List<Recharge> rechargeList = rechargeRepository.select(condition);
        Assert.isTrue(rechargeList.size() == 1, "Recharge queryByOutTradeId not unique, outTradeId: " + outTradeId);
        return rechargeList.get(0);
    }


}
