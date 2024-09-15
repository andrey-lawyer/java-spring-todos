package com.todos.exception;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
  private String message;
  private Map<String, String> errors = new HashMap<>();
  
  public ErrorResponse(String message) {
    this.message = message;
  }
  
  public void addError(String field, String errorMessage) {
    errors.put(field, errorMessage);
  }
}
