package me.Sokhra.sokhrabackendspring.trip.repository.projection;

import me.Sokhra.sokhrabackendspring.trip.model.Place;
import me.Sokhra.sokhrabackendspring.trip.model.TripStatus;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface TripProjectionForTripsListing {


  Place getOrigin();

  Place getDestination();

  LocalDate getDepartureDate();

  @Value("#{@tripService.getAvailableWeight(target.id)}")
  Integer getAvailableWeight();

  Integer getPrice();

  TripStatus getStatus();
}
