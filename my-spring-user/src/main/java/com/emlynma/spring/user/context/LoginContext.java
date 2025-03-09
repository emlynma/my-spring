package com.emlynma.spring.user.context;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Data
@Component
@Scope("request")
public class LoginContext {
    private String id = new Random().nextInt(1000) + "";

    private String username;
}
