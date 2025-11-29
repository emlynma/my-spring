package com.emlyn.spring.trade.handler.context;

import com.emlyn.spring.common.handler.context.BizContext;
import com.emlyn.spring.data.domain.entity.Recharge;
import com.emlyn.spring.data.domain.enums.TradeType;
import com.emlyn.spring.data.domain.enums.status.RechargeStatus;
import com.emlyn.spring.trade.domain.model.UserInfo;
import com.emlyn.spring.trade.handler.request.RechargeRequest;
import com.emlyn.spring.trade.handler.response.RechargeResponse;
import lombok.Data;

@Data
public class RechargeContext implements BizContext<RechargeRequest, RechargeResponse> {

    private RechargeRequest request;

    private RechargeResponse response;

    private UserInfo userInfo;

    private Recharge recharge;

    public static RechargeContext create(RechargeRequest request) {
        RechargeContext context = new RechargeContext();
        context.setRequest(request);
        return context;
    }

    public Recharge buildRecharge() {
        Recharge recharge = new Recharge();
        recharge.setChannelId(request.getChannelId());
        recharge.setOutTradeId(request.getOutTradeId());
        recharge.setUid(request.getUid());
        recharge.setAmount(request.getAmount());
        recharge.setCurrency(request.getCurrency());
        recharge.setTradeType(TradeType.RECHARGE);
        recharge.setStatus(RechargeStatus.INITIAL);
        return recharge;
    }

    public RechargeResponse buildResponse() {
        RechargeResponse response = new RechargeResponse();
        response.setTradeId(recharge.getTradeId());
        response.setStatus(recharge.getStatus());
        if (recharge.isCompleted()) {
            response.setErrorCode(recharge.getErrorCode());
            response.setErrorDesc(recharge.getErrorDesc());
        }
        return response;
    }

}
