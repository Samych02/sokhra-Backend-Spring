package org.example.sokhrabackendspring.trip.repository.projection;

import org.example.sokhrabackendspring.trip.model.Place;
import org.example.sokhrabackendspring.trip.model.TripStatus;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface TripProjectionForMyTrips {
  LocalDateTime getCreatedAt();

  UUID getId();

  Place getOrigin();

  Place getDestination();

  LocalDate getDepartureDate();

  Integer getMaxWeight();

  @Value("#{@tripService.getAvailableWeight(target.id)}")
  Integer getAvailableWeight();

  Integer getPrice();

  TripStatus getStatus();
}
