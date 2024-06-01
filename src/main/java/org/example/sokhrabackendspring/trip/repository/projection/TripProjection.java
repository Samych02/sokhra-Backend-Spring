package org.example.sokhrabackendspring.trip.repository.projection;

import org.example.sokhrabackendspring.trip.model.Place;
import org.example.sokhrabackendspring.trip.model.TripStatus;
import org.example.sokhrabackendspring.user.repository.projection.UserProjection;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface TripProjection {
  LocalDateTime getCreatedAt();

  UUID getId();

  UserProjection getTraveller();

  Place getOrigin();

  Place getDestination();

  LocalDate getDepartureDate();

  @Value("#{@tripService.getAvailableWeight(target.id)}")
  Integer getAvailableWeight();

  Integer getPrice();

  TripStatus getStatus();

//    @Value("#{target.traveller.id}")
//    String getIdd();
//  public interface user{
//    String getFirstName();
//    String getProfilePicture();
//
////    @Value("#{@reviewService.getRating(target)}")
////    Rating getRating();
//  }
}
