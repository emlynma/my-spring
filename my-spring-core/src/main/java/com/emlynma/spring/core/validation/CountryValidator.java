package com.emlynma.spring.core.validation;

import com.emlynma.spring.core.constant.CountryEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CountryValidator implements ConstraintValidator<CountryValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Arrays.stream(CountryEnum.values())
                .anyMatch(country -> country.name().equals(value));
    }

}
