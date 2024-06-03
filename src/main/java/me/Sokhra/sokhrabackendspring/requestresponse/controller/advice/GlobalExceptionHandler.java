package org.example.sokhrabackendspring.requestresponse.controller.advice;

import org.example.sokhrabackendspring.requestresponse.model.StandardResponse;
import org.example.sokhrabackendspring.requestresponse.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<StandardResponse.ErrorResponse> handleAllExceptions(Exception exception) {
    return ResponseEntity.badRequest().body(ResponseUtil.errorResponse(exception.getMessage()));
  }
}
