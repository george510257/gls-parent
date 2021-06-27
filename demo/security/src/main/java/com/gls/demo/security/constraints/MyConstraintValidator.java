package com.gls.demo.security.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author george
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("value = " + value + ", context = " + context);
        return false;
    }

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("MyConstraint initialize");
    }
}
