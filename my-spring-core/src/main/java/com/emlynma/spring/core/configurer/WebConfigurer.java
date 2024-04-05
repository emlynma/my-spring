package com.emlynma.spring.core.configurer;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootConfiguration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Disable StringHttpMessageConverter
        converters.removeIf(converter -> converter.getClass().isAssignableFrom(StringHttpMessageConverter.class));
    }
}
