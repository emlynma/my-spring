package com.emlyn.spring.common.handler.exception;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BizExceptionHandlerRegistry {

    private final List<BizExceptionHandler<?, ?>> bizExceptionHandlerList;

    private final Map<Class<?>, Map<Class<?>, BizExceptionHandler<?, ?>>> bizExceptionHandlerMap = new HashMap<>();

    @PostConstruct
    private void register() {
        for (var exceptionHandler : bizExceptionHandlerList) {
            Class<?> bizHandlerType = exceptionHandler.getBizHandlerType();
            Class<?> exceptionType  = exceptionHandler.getExceptionType();
            bizExceptionHandlerMap.computeIfAbsent(bizHandlerType, k -> new HashMap<>()).put(exceptionType, exceptionHandler);
        }
    }

    public BizExceptionHandler<?, ?> getHandler(Class<?> handlerType, Class<?> exceptionType) {
        return bizExceptionHandlerMap.getOrDefault(handlerType, Map.of()).get(exceptionType);
    }

}
