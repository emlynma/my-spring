package com.emlyn.spring.common.handler.exception;

import com.emlyn.spring.common.exception.BizException;
import com.emlyn.spring.common.handler.BizHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BizExceptionDispatcher {

    private final BizExceptionHandlerRegistry bizExceptionHandlerRegistry;

    @SuppressWarnings("unchecked")
    public <T> T dispatch(BizHandler<?, ?> handler, BizException exception) {
        var exceptionHandler = (BizExceptionHandler<BizException, ?>) bizExceptionHandlerRegistry.getHandler(handler.getClass(), exception.getClass());
        if (exceptionHandler != null) {
            return (T) exceptionHandler.handle(exception);
        }
        throw exception;
    }

}
