package com.cpimgroup.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioEmailException extends RuntimeException {

  public UsuarioEmailException(String message) {
    super(message);
  }
}
