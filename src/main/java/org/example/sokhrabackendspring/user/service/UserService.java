package org.example.sokhrabackendspring.user.service;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.service.ImageService;
import org.example.sokhrabackendspring.user.entity.User;
import org.example.sokhrabackendspring.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final ImageService imageService;


  public Boolean shouldRegister(String uid) {
    return !userRepository.existsByUid(uid);
  }

  public void registerUser(User user, String profilePicture) throws IOException {
    saveProfilePicture(profilePicture, user.getUid());
    userRepository.save(user);
  }

  public String getProfilePicture(String uid) throws IOException {
    return imageService.loadImage(uid);
  }

  public void saveProfilePicture(String uid, String profilePicture) throws IOException {
    imageService.saveImage(profilePicture, uid);
  }


}
