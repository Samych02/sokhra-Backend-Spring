package org.example.sokhrabackendspring.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UserDTO {
  @Data
  @Builder
  public static class RegistrationDTO {
    @NotEmpty(message = "firstname is required.")
    private String firstName;

    @NotEmpty(message = "lastname is required.")
    private String lastName;

    @NotEmpty
    private MultipartFile profilePicture;
  }



}
