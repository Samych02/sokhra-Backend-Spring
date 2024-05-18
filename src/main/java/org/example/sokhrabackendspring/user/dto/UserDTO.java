package org.example.sokhrabackendspring.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UserDTO {
  @Data
  @Builder
  public static class RegistrationDTO {
    @NotBlank(message = "firstname is required.")
    private String firstName;

    @NotBlank(message = "lastname is required.")
    private String lastName;

    @NotEmpty
    private MultipartFile profilePicture;
  }



}
