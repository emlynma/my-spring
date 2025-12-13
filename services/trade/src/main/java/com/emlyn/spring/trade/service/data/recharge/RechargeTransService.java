package com.emlyn.spring.trade.service.data.recharge;

import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.domain.enums.status.RechargeStatus;

public interface RechargeTransService {

    /**
     * 创建单据
     */
    Recharge createRecharge(Recharge recharge);

    /**
     * 更新单据信息，但不更新状态
     */
    void updateRecharge(Recharge recharge, Recharge update);

    /**
     * 推进单据状态
     */
    void advanceRechargeStatus(Recharge recharge, Recharge update, RechargeStatus status);

}
