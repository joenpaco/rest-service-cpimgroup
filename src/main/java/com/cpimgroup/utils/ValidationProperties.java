package com.cpimgroup.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidationProperties {

  @Value("${validation.email.regexp}")
  private String emailRegexp;

  @Value("${validation.email.message}")
  private String emailMessage;

  @Value("${validation.password.regexp}")
  private String passwordRegexp;

  @Value("${validation.password.message}")
  private String passwordMessage;

  public String getPasswordRegexp() {
    return passwordRegexp;
  }

  public String getPasswordMessage() {
    return passwordMessage;
  }

  public String getEmailRegexp() {
    return emailRegexp;
  }

  public String getEmailMessage() {
    return emailMessage;
  }
}
