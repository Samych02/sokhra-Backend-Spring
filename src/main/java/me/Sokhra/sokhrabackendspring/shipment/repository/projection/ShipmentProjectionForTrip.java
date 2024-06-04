package me.Sokhra.sokhrabackendspring.shipment.repository.projection;

import me.Sokhra.sokhrabackendspring.shipment.model.ShipmentStatus;
import me.Sokhra.sokhrabackendspring.user.repository.projection.UserProjection;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface ShipmentProjectionForTrip {
  UUID getId();

  String getTitle();

  Integer getWeight();

  UserProjection getTripTraveller();

  ShipmentStatus getStatus();

  @Value("#{@tripRepository.getTripById(target.trip.id).price * target.weight}")
  Integer getPrice();
}
