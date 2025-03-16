package com.emlynma.spring.user.handler.context;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContextHolder {

    private final ObjectFactory<LoginContext> loginContextFactory;

    public LoginContext getLoginContext() {
        return loginContextFactory.getObject();
    }

}
