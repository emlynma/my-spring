package com.emlyn.spring.exchange.service.record.impl;

import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.util.AssertUtils;
import com.emlyn.spring.data.component.id.DataIdGenerator;
import com.emlyn.spring.data.domain.entity.Exchange;
import com.emlyn.spring.data.domain.enums.status.ExchangeStatus;
import com.emlyn.spring.data.repository.ExchangeRepository;
import com.emlyn.spring.exchange.constant.ExchangeErrorCode;
import com.emlyn.spring.exchange.service.record.ExchangeTransService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExchangeTransServiceImpl implements ExchangeTransService {

    private final ExchangeRepository exchangeRepository;

    private final DataIdGenerator recordDataIdGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Exchange createExchange(Exchange exchange) {
        // exchangeId 由调用方传入时沿用（作为幂等键），否则按 tradeId 生成
        if (!StringUtils.hasText(exchange.getExchangeId())) {
            exchange.setExchangeId(recordDataIdGenerator.generateExchangeIdWithTradeId(exchange.getTradeId()));
        }
        exchangeRepository.insert(exchange);
        return exchange;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExchange(Exchange exchange, Exchange update) {
        // 状态校验
        AssertUtils.isTrue(update.getStatus() == null, ExchangeErrorCode.EXCHANGE_STATUS_INVALID);
        // 执行更新
        exchangeRepository.update(exchange, update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void advanceExchangeStatus(Exchange exchange, Exchange update, ExchangeStatus status) {
        // 重入校验
        if (exchange.getStatus() == status) {
            return;
        }
        // 状态校验
        AssertUtils.isTrue(ExchangeStatus.checkReachability(exchange.getStatus(), status), ExchangeErrorCode.EXCHANGE_STATUS_INVALID);
        // 更新信息
        update.setStatus(status);
        if (ExchangeStatus.checkFinal(status)) {
            update.setFinishTime(new Date());
            setDefaultErrorCode(update);
        }
        // 执行更新
        exchangeRepository.update(exchange, update);
    }

    private void setDefaultErrorCode(Exchange update) {
        if (ExchangeStatus.SUCCESS == update.getStatus()) {
            update.setError(SysErrorCode.SUCCESS);
        }
        if (ExchangeStatus.FAILURE == update.getStatus()) {
            if (update.getErrorCode() != null) {
                return;
            }
            update.setError(SysErrorCode.UNKNOWN);
        }
    }

}
