package org.example.sokhrabackendspring.imageutility.service;

import org.example.sokhrabackendspring.imageutility.model.ImageNature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {
  //to choose in which folder to save or retrieve pictures
  @Value("${profile-picture-directory}")
  private String profilePictureDirectory;

  @Value("${request-picture-directory}")
  private String requestPictureDirectory;

  public void saveImage(MultipartFile image, String fileName, ImageNature imageNature) throws IOException {
    byte[] imageData = image.getBytes();
    Path path;
    if (imageNature == ImageNature.PROFIL) path = Paths.get(profilePictureDirectory + fileName);
    else path = Paths.get(requestPictureDirectory + fileName);
    Files.write(path, imageData);
  }

  public byte[] loadImage(String filename, ImageNature imageNature) throws IOException {
    Path filePath;
    if (imageNature == ImageNature.PROFIL) filePath = Paths.get(profilePictureDirectory).resolve(filename);
    else filePath = Paths.get(requestPictureDirectory).resolve(filename);
    return Files.readAllBytes(filePath);
  }
}
