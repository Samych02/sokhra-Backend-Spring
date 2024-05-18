package org.example.sokhrabackendspring.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
  @NotBlank(message = "firstname is required.")
  private String firstName;

  @NotBlank(message = "lastname is required.")
  private String lastName;

  @NotEmpty
  private String profilePicture;
}
