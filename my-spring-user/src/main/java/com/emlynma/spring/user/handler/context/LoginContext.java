package com.emlynma.spring.user.handler.context;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Random;

@Data
@Component
@RequestScope
public class LoginContext {
    private String id = new Random().nextInt(1000) + "";

    private String username;
}
