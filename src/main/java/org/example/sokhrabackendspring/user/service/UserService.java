package org.example.sokhrabackendspring.user.service;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.imageutility.model.ImageNature;
import org.example.sokhrabackendspring.imageutility.service.ImageService;
import org.example.sokhrabackendspring.user.dto.UserDTO;
import org.example.sokhrabackendspring.user.entity.User;
import org.example.sokhrabackendspring.user.repository.UserRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final ImageService imageService;


  public Boolean shouldRegister(Jwt token) {
    return !userRepository.existsById(token.getClaim("user_id"));
  }

  public void registerUser(Jwt token, UserDTO.RegistrationDTO registrationDTO)
          throws IOException {
    String uid = token.getClaim("user_id");
    String phoneNumber = token.getClaim("phone_number");
    User user = User
            .builder()
            .id(uid)
            .phoneNumber(phoneNumber)
            .firstName(registrationDTO.getFirstName())
            .lastName(registrationDTO.getLastName())
            .profilePicture(
                    uid + "." + Objects.requireNonNull(registrationDTO.getProfilePicture().getOriginalFilename()).split("\\.")[1].toLowerCase()
            )
            .build();
    saveProfilePicture(registrationDTO.getProfilePicture(), user.getProfilePicture());
    userRepository.save(user);
  }

  public byte[] getProfilePicture(String uid)
          throws IOException {
    String profilePicture = userRepository.getProfilePictureByUid(uid);
    return imageService.loadImage(profilePicture, ImageNature.PROFIL);
  }

  public void saveProfilePicture(MultipartFile profilePicture,
                                 String uid)
          throws IOException {
    imageService.saveImage(profilePicture, uid, ImageNature.PROFIL);
  }


}
