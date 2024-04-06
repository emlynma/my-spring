package com.emlynma.spring.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<CustomValid, Validatable> {

    @Override
    public boolean isValid(Validatable value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.validate();
    }

}
