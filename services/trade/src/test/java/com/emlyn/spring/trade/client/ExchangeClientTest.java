package com.emlyn.spring.trade.client;

import com.emlyn.spring.common.contract.exchange.request.PostingRequest;
import com.emlyn.spring.common.contract.exchange.response.ExchangeResponse;
import com.emlyn.spring.common.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExchangeClientTest {

    @Autowired
    private ExchangeClient exchangeClient;

    @Test
    void test() {
        PostingRequest request = new PostingRequest();
        request.setExchangeId("1234567890");
        ExchangeResponse response = exchangeClient.posting(request);
        System.out.println(JsonUtils.toJson(response));
    }

}
