package com.cpimgroup.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private ValidationProperties validationProperties;

  @Override
  public void initialize(ValidEmail constraintAnnotation) {
    this.validationProperties = SpringContextHolder.getBean(ValidationProperties.class);
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (validationProperties == null) {
      throw new IllegalStateException("ValidationProperties no se inyectó correctamente");
    }

    String regex = validationProperties.getEmailRegexp();

    if (email == null || !email.matches(regex)) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(validationProperties.getEmailMessage())
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
