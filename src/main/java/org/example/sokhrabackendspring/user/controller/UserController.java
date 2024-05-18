package org.example.sokhrabackendspring.user.controller;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
public class UserController {
  private final UserService userService;

  @GetMapping("/user/register/shouldRegister")
  public ResponseEntity<?> shouldRegister(@AuthenticationPrincipal Jwt token) {
    String uid = token.getClaim("user_id");
    Boolean shouldRegister = userService.shouldRegister(uid);
    return ResponseEntity.ok(shouldRegister);
  }

  @PostMapping("/user/register")
  public ResponseEntity<?> register(@AuthenticationPrincipal Jwt token, @RequestBody UserDTO userDTO) {
    String uid = token.getClaim("user_id");
    String phoneNumber = token.getClaim("phone_number");
    User user = new User(uid, phoneNumber, userDTO);
    System.out.println(userDTO);
    try {
      userService.registerUser(user, userDTO.getProfilePicture());
      return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    } catch (IOException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = "user/profile/image/{uid}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<?> getProfilePicture(@PathVariable String uid) {
    try {
      return ResponseEntity.ok(userService.getProfilePicture(uid));
    } catch (IOException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
