package com.emlynma.spring.core.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=CountryValidator.class)
public @interface CountryValid {

    String message() default "invalid country";

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

}
