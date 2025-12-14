package com.emlyn.spring.trade.client;

import com.emlyn.spring.common.contract.ApiResponse;
import com.emlyn.spring.common.contract.exchange.request.PostingRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import com.emlyn.spring.common.error.SysErrorCode;
import com.emlyn.spring.common.exception.BizException;
import com.emlyn.spring.common.util.AssertUtils;
import feign.FeignException;
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
            ApiResponse<ExchangeResponse> apiResponse = exchangeFeign.posting(request);
            AssertUtils.isTrue(apiResponse.successful(), SysErrorCode.RPC_ILLEGAL);
            return apiResponse.getData();
        } catch (RetryableException e) {
            throw new BizException(SysErrorCode.RPC_TIMEOUT);
        } catch (FeignException e) {
            throw new BizException(SysErrorCode.RPC_FAILURE);
        } catch (Exception e) {
            log.error("ExchangeClient posting exception:", e);
            return null;
        }
    }

}
