package com.emlyn.spring.transfer.handler;

import com.emlyn.spring.common.handler.AbstractBizHandler;
import com.emlyn.spring.common.handler.annotation.Handler;
import com.emlyn.spring.data.component.lock.LockFactory;
import com.emlyn.spring.transfer.domain.model.UserInfo;
import com.emlyn.spring.transfer.handler.context.RechargeContext;
import com.emlyn.spring.transfer.handler.request.RechargeRequest;
import com.emlyn.spring.transfer.handler.response.RechargeResponse;
import com.emlyn.spring.transfer.service.UserService;
import com.emlyn.spring.transfer.service.data.recharge.RechargeCheckService;
import com.emlyn.spring.transfer.service.data.recharge.RechargeTransService;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class RechargeHandler extends AbstractBizHandler<RechargeRequest, RechargeResponse> {

    private final RechargeCheckService rechargeCheckService;
    private final RechargeTransService rechargeTransService;
    private final UserService userService;
    private final LockFactory lockFactory;

    @Override
    protected RechargeResponse doHandle(RechargeRequest request) {

        // 创建上下文
        var context = RechargeContext.create(request);

        // 幂等校验
        rechargeCheckService.checkCreateIdempotency(request.getOutTradeId());

        // 查询用户信息
        UserInfo userInfo = userService.queryUserInfo(request.getUid());
        context.setUserInfo(userInfo);

        // 创建单据
        var recharge = rechargeTransService.createRecharge(context.buildRecharge());
        context.setRecharge(recharge);

        // 加锁
        locked(lockFactory.createRechargeLock(recharge.getTradeId()));

        // 查询XXX信息

        // 校验XXX数据

        // 发送充值消息

        // 构建响应结果
        return context.buildResponse();
    }

}
