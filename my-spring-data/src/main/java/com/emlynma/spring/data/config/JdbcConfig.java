package com.emlynma.spring.data.config;

import com.emlynma.spring.data.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableJdbcRepositories(basePackages = "com.emlynma.spring.data.repository")
public class JdbcConfig extends AbstractJdbcConfiguration {

    @Override @NonNull
    protected List<?> userConverters() {
        return Arrays.asList(
                new User.ExtraInfoReadingConverter(),
                new User.ExtraInfoWritingConverter()
        );
    }

}
