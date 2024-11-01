package com.cpimgroup.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

  private ValidationProperties validationProperties;

  @Override
  public void initialize(ValidPassword constraintAnnotation) {
    this.validationProperties = SpringContextHolder.getBean(ValidationProperties.class);
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (validationProperties == null) {
      throw new IllegalStateException("ValidationProperties no se inyectó correctamente");
    }

    String regex = validationProperties.getPasswordRegexp();

    if (password == null || !password.matches(regex)) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(validationProperties.getPasswordMessage())
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
