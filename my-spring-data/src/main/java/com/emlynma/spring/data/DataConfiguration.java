package com.emlynma.spring.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.emlynma.spring.data.mapper")
public class DataConfiguration {
}
