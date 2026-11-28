package com.emlyn.spring.transfer.service.data.recharge.impl;

import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.repository.RechargeRepository;
import com.emlyn.spring.transfer.service.data.recharge.RechargeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RechargeQueryServiceImpl implements RechargeQueryService {

    private final RechargeRepository rechargeRepository;

    @Override
    public Recharge queryByTradeId(String tradeId) {
        if (!StringUtils.hasText(tradeId)) {
            return null;
        }
        Recharge condition = new Recharge();
        condition.setTradeId(tradeId);
        return rechargeRepository.selectOne(condition);
    }

    @Override
    public Recharge queryByOutTradeId(String outTradeId) {
        if (!StringUtils.hasText(outTradeId)) {
            return null;
        }
        Recharge condition = new Recharge();
        condition.setOutTradeId(outTradeId);
        return rechargeRepository.selectOne(condition);
    }


}
