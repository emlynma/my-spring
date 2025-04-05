package com.emlynma.spring.core.component.id;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class IdGenerator {

    private Snowflake snowflake;

    @PostConstruct
    public void init() {
        snowflake = new Snowflake(getCenterId(), getWorkerId());
    }

    public Long generateId() {
        return snowflake.nextId();
    }

    private int getCenterId() {
        return 1;
    }

    private int getWorkerId() {
        int workerId = 0;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String hostAddress = ip.getHostAddress();
            for (char c : hostAddress.toCharArray()) {
                workerId += c;
            }
        } catch (UnknownHostException e) {
            log.warn("get workerId fail, rollback random number", e);
            workerId = (int) (Math.random() * Integer.MAX_VALUE);
        }
        return workerId % 32; // 限制 workerId 在 0-31 之间（5位）
    }

}
