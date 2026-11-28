package com.emlyn.spring.transfer.service.data.recharge.impl;

import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.util.AssertUtils;
import com.emlyn.spring.data.component.id.DataIdGenerator;
import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.domain.enums.status.RechargeStatus;
import com.emlyn.spring.transfer.error.TransferErrorCode;
import com.emlyn.spring.data.repository.RechargeRepository;
import com.emlyn.spring.transfer.service.data.recharge.RechargeTransService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RechargeTransServiceImpl implements RechargeTransService {

    private final RechargeRepository rechargeRepository;

    private final DataIdGenerator recordDataIdGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Recharge createRecharge(Recharge recharge) {
        recharge.setTradeId(recordDataIdGenerator.generateTradeIdWithOuterId(recharge.getTradeType(), recharge.getOutTradeId()));
        rechargeRepository.insert(recharge);
        return recharge;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRecharge(Recharge recharge, Recharge update) {
        // 状态校验
        AssertUtils.isTrue(update.getStatus() == null, TransferErrorCode.RECHARGE_STATUS_INVALID);
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
        AssertUtils.isTrue(RechargeStatus.checkReachability(recharge.getStatus(), status), TransferErrorCode.RECHARGE_STATUS_INVALID);
        // 更新信息
        update.setStatus(status);
        if (RechargeStatus.checkFinal(status)) {
            update.setFinishTime(new Date());
            setDefaultErrorCode(update);
        }
        // 执行更新
        rechargeRepository.update(recharge, update);
    }

    private void setDefaultErrorCode(Recharge update) {
        if (RechargeStatus.SUCCESS == update.getStatus()) {
            update.setError(SysErrorCode.SUCCESS);
        }
        if (RechargeStatus.REFUSED == update.getStatus()) {
            if (update.getErrorCode() != null) {
                return;
            }
            update.setError(SysErrorCode.UNKNOWN);
        }
    }

}
