package com.emlynma.spring.core.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=CustomValidator.class)
public @interface CustomValid {

    String message() default "invalid custom value";

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

}
