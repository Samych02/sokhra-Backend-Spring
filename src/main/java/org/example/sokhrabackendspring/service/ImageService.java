package org.example.sokhrabackendspring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {
  @Value("${profile-picture-directory}")
  private String imageDirectory;

  public void saveImage(MultipartFile image, String fileName) throws IOException {
    byte[] imageData = image.getBytes();
    Path path = Paths.get(imageDirectory + fileName);
    Files.write(path, imageData);
  }

  public byte[] loadImage(String filename) throws IOException {
    Path filePath = Paths.get(imageDirectory).resolve(filename);
    return Files.readAllBytes(filePath);
  }
}
