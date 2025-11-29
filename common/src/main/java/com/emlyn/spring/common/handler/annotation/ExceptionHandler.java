package com.emlyn.spring.common.handler.annotation;

import com.emlyn.spring.common.handler.BizHandler;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandler {

    Class<? extends BizHandler<?,?>> value();

}
