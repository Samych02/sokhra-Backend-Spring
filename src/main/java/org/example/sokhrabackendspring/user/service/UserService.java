package org.example.sokhrabackendspring.user.service;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.service.ImageService;
import org.example.sokhrabackendspring.user.entity.User;
import org.example.sokhrabackendspring.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final ImageService imageService;


  public Boolean shouldRegister(String uid) {
    return !userRepository.existsByUid(uid);
  }

  public void registerUser(User user,
                           MultipartFile profilePicture)
          throws IOException {
    saveProfilePicture(profilePicture, user.getProfilePicture());
    userRepository.save(user);
  }

  public byte[] getProfilePicture(String uid)
          throws IOException {
    String profilePicture = userRepository.getProfilePictureByUid(uid);
    return imageService.loadImage(profilePicture);
  }

  public void saveProfilePicture(MultipartFile profilePicture,
                                 String uid)
          throws IOException {
    imageService.saveImage(profilePicture, uid);
  }


}
