package com.emlynma.spring.core.id;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private Snowflake snowflake;

    @PostConstruct
    public void init() {
        snowflake = new Snowflake(1, 1);
    }

    public Long generateUid() {
        return snowflake.nextId();
    }

}
