package com.emlyn.spring.trade.client;

import com.emlyn.spring.common.contract.exchange.request.PostingRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.trade.client.feign.ExchangeFeign;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeClient {

    private final ExchangeFeign exchangeFeign;

    public ExchangeResponse posting(PostingRequest request) {
        try {
            return exchangeFeign.posting(request).getDataOrThrow();
        } catch (RetryableException e) {
            log.error("{}", SysErrorCode.RPC_TIMEOUT, e);
            return null;
        } catch (Exception e) {
            log.error("{}", SysErrorCode.RPC_FAILURE, e);
            return null;
        }
    }

}
