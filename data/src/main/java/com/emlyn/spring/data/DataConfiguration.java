package com.emlyn.spring.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.emlyn.spring.data.mapper")
public class DataConfiguration {
}
