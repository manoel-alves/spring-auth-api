package com.manoel.springauthapi.validation;

import com.manoel.springauthapi.config.PasswordPolicyProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Autowired
    private PasswordPolicyProperties properties;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;

        if (password.length() < properties.getMinLength()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Password must be at least " + properties.getMinLength() + " characters")
                    .addConstraintViolation();
            return false;
        }

        if (!password.matches(properties.getRegex())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(properties.getMessage())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
