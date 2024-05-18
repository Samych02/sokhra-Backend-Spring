package org.example.sokhrabackendspring.response.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class StandardResponse {
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class SuccessResponse<T> {
    private String status;
    private String message;
    private T data;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ErrorResponse {
    private String status;
    private String errorMessage;
  }
}
