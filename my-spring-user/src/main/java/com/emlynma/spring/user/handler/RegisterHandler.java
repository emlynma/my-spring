package com.emlynma.spring.user.handler;

import com.emlynma.spring.core.annotation.Handler;
import com.emlynma.spring.data.repository.UserRepository;
import com.emlynma.spring.user.contract.request.RegisterRequest;
import com.emlynma.spring.user.contract.response.RegisterResponse;
import com.emlynma.spring.user.handler.context.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Handler
@RequiredArgsConstructor
public class RegisterHandler {

    private final ContextHolder contextHolder;

    private UserRepository userRepository;

    public RegisterResponse handle(RegisterRequest request) {

        return null;
    }

}
