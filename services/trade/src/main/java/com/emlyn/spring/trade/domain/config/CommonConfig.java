package com.emlyn.spring.trade.domain.config;

import com.alibaba.cloud.nacos.annotation.NacosConfig;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@NacosConfig(group = "trade", dataId = "common.json")
public class CommonConfig {

    private String name;
    private String desc;

}
