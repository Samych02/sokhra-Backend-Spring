package org.example.sokhrabackendspring.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseAdminSDKConfiguration {
  private final ResourceLoader resourceLoader;

  public FirebaseAdminSDKConfiguration(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Bean
  public FirebaseApp initialization() throws IOException {
    final Resource fileResource = resourceLoader.getResource("classpath:/firebase-adminsdk.json");
    InputStream serviceAccount = fileResource.getInputStream();

    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();
    return FirebaseApp.initializeApp(options);
  }
}
