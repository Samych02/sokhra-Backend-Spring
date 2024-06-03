package me.Sokhra.sokhrabackendspring.requestresponse.util;

import me.Sokhra.sokhrabackendspring.requestresponse.model.StandardResponse;

public class ResponseUtil {
  public static <T> StandardResponse.SuccessResponse<T> successResponse(String message, T data) {
    return StandardResponse.SuccessResponse.<T>builder().status("success").statusText(message).data(data).build();
  }

  public static StandardResponse.ErrorResponse errorResponse(String errorMessage) {
    return StandardResponse.ErrorResponse.builder().status("error").statusText(errorMessage).build();
  }
}
