package com.emlynma.spring.user.handler;

import com.emlynma.spring.core.annotation.Handler;
import com.emlynma.spring.user.context.LoginContext;
import com.emlynma.spring.user.context.holder.ContextHolder;
import com.emlynma.spring.user.contract.request.LoginRequest;
import com.emlynma.spring.user.contract.response.LoginResponse;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class AuthHandler {

    private final ContextHolder contextHolder;

    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();
        LoginContext loginContext = contextHolder.getLoginContext();
        response.setToken(loginContext.getId());
        return response;
    }

}
