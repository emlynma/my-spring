package com.emlyn.spring.trade.service.data.recharge.impl;

import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.util.AssertUtils;
import com.emlyn.spring.data.component.id.TradeIdGenerator;
import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.domain.enums.status.RechargeStatus;
import com.emlyn.spring.trade.error.TradeErrorCode;
import com.emlyn.spring.data.repository.RechargeRepository;
import com.emlyn.spring.trade.service.data.recharge.RechargeTransService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RechargeTransServiceImpl implements RechargeTransService {

    private final RechargeRepository rechargeRepository;

    private final TradeIdGenerator tradeIdGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Recharge createRecharge(Recharge recharge) {
        recharge.setTradeId(tradeIdGenerator.generateTradeIdWithOuterId(recharge.getTradeType(), recharge.getOutTradeId()));
        rechargeRepository.insert(recharge);
        return recharge;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRecharge(Recharge recharge, Recharge update) {
        // 状态校验
        AssertUtils.isTrue(update.getStatus() == null, TradeErrorCode.RECHARGE_STATUS_INVALID);
        // 执行更新
        rechargeRepository.update(recharge, update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void advanceRechargeStatus(Recharge recharge, Recharge update, RechargeStatus status) {
        // 重入校验
        if (recharge.getStatus() == status) {
            return;
        }
        // 状态校验
        AssertUtils.isTrue(RechargeStatus.checkReachability(recharge.getStatus(), status), TradeErrorCode.RECHARGE_STATUS_INVALID);
        // 更新信息
        update.setStatus(status);
        if (RechargeStatus.checkFinal(status)) {
            update.setFinishTime(new Date());
            if (RechargeStatus.SUCCESS == status) update.setError(SysErrorCode.SUCCESS);
        }
        // 执行更新
        rechargeRepository.update(recharge, update);
    }

}
