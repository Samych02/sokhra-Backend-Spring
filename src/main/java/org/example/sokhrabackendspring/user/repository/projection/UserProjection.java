package org.example.sokhrabackendspring.user.repository.projection;

public interface UserProjection {
  String getId();

  String getFirstName();

  String getProfilePicture();

//  @Value("#{@reviewService.getRating(target.id)}")
//  Rating getRating();
}
