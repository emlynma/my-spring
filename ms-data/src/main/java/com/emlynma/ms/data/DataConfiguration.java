package com.emlynma.ms.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.emlynma.ms.data.mapper")
public class DataConfiguration {
}
