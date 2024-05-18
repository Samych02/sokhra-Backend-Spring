package org.example.sokhrabackendspring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImageService {
  @Value("${profile-picture-directory}")
  private String imageDirectory;

  public void saveImage(String image, String uid) throws IOException {
    byte[] imageData = Base64.getDecoder().decode(image);
    Path path = Paths.get(imageDirectory + uid);
    Files.write(path, imageData);
  }

  public String loadImage(String filename) throws IOException {
    Path filePath = Paths.get(imageDirectory).resolve(filename);
    return Base64.getEncoder().encodeToString(Files.readAllBytes(filePath));
  }
}
