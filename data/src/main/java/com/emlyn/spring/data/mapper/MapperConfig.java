package com.emlyn.spring.data.mapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.emlyn.spring.data.mapper")
public class MapperConfig {
}
