package org.example.sokhrabackendspring.user.repository.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserProfileProjection {
  String getId();

  String getFirstName();

  String getProfilePicture();

  @Value("#{@reviewService.getAllUserReviews(target.id)}")
  List<ReviewProjection> getRatings();
}
