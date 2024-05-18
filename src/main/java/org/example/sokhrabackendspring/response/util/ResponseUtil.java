package org.example.sokhrabackendspring.response.util;

import org.example.sokhrabackendspring.response.model.StandardResponse;

public class ResponseUtil {
  public static <T> StandardResponse.SuccessResponse<T> successResponse(String message, T data) {
    return StandardResponse.SuccessResponse.<T>builder().status("success").message(message).data(data).build();
  }

  public static StandardResponse.ErrorResponse errorResponse(String errorMessage) {
    return StandardResponse.ErrorResponse.builder().status("error").errorMessage(errorMessage).build();
  }
}
