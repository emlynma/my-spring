package com.emlyn.spring.common.handler.exception;

import com.emlyn.spring.common.exception.BizException;
import com.emlyn.spring.common.handler.annotation.ExceptionHandler;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.ResolvableType;

public interface BizExceptionHandler<Exception extends BizException, Response> {

    Response handle(Exception exception);

    default Class<?> getBizHandlerType() {
        Class<?> exceptionHandlerType = AopUtils.getTargetClass(this);
        ExceptionHandler annotation = exceptionHandlerType.getAnnotation(ExceptionHandler.class);
        return annotation == null ? null : annotation.value();
    }

    default Class<?> getExceptionType() {
        Class<?> exceptionHandlerType = AopUtils.getTargetClass(this);
        ResolvableType resolvableType = ResolvableType.forClass(exceptionHandlerType).as(BizExceptionHandler.class);
        return resolvableType.getGeneric(0).resolve();
    }

}
