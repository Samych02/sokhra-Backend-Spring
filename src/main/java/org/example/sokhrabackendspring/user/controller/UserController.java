package org.example.sokhrabackendspring.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.response.util.ResponseUtil;
import org.example.sokhrabackendspring.user.dto.UserDTO;
import org.example.sokhrabackendspring.user.entity.User;
import org.example.sokhrabackendspring.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class UserController {
  private final UserService userService;

  @GetMapping("/user/register/shouldRegister")
  public ResponseEntity<?> shouldRegister(@AuthenticationPrincipal Jwt token) {
    String uid = token.getClaim("user_id");
    Boolean shouldRegister = userService.shouldRegister(uid);
    return ResponseEntity.ok(
            ResponseUtil.successResponse(
                    (shouldRegister) ? "User should register" : "User should not register",
                    Collections.singletonMap("shouldRegister", shouldRegister)
            )
    );
  }

  @PostMapping("/user/register")
  public ResponseEntity<?> register(@AuthenticationPrincipal Jwt token,
                                    @ModelAttribute UserDTO.RegistrationDTO registrationDTO)
          throws IOException {
    String uid = token.getClaim("user_id");
    String phoneNumber = token.getClaim("phone_number");
    User user = User
            .builder()
            .uid(uid)
            .phoneNumber(phoneNumber)
            .firstName(registrationDTO.getFirstName())
            .lastName(registrationDTO.getLastName())
            .profilePicture(
                    uid + "." + Objects.requireNonNull(registrationDTO.getProfilePicture().getOriginalFilename()).split("\\.")[1]
            )
            .build();
    userService.registerUser(user, registrationDTO.getProfilePicture());
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    ResponseUtil.successResponse(
                            "User created successfully",
                            null
                    )
            );
  }

  @GetMapping(value = "user/profile/image/{uid}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<?> getProfilePicture(@PathVariable String uid) throws IOException {
    return ResponseEntity.ok(
            userService.getProfilePicture(uid)
    );
  }
}
