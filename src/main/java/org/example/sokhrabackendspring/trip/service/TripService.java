package org.example.sokhrabackendspring.trip.service;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.shipment.entity.Request;
import org.example.sokhrabackendspring.shipment.model.RequestStatus;
import org.example.sokhrabackendspring.trip.dto.TripDTO;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.trip.exceptions.CantEditTrip;
import org.example.sokhrabackendspring.trip.exceptions.TripNotFoundException;
import org.example.sokhrabackendspring.trip.model.TripStatus;
import org.example.sokhrabackendspring.trip.repository.TripRepository;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {
  private final TripRepository tripRepository;

  public List<Trip> getAllTrips() {
    return tripRepository.findAll();
  }

  public Trip getTripById(UUID id) {
    return tripRepository.findById(id).orElse(null);
  }

  public void addTrip(Jwt token, TripDTO.addTripDTO addTripDTO) {
    User traveller = new User(token.getClaim("user_id"));

    Trip trip = Trip.builder()
            .traveller(traveller)
            .origin(addTripDTO.getOrigin())
            .destination(addTripDTO.getDestination())
            .maxWeight(addTripDTO.getWeight())
            .price(addTripDTO.getPrice())
            .status(TripStatus.ACTIVE)
            .build();
    tripRepository.save(trip);
  }

  public double getAvailableWeight(UUID id) throws Exception {
    Trip trip = getTripById(id);
    if (trip == null) throw new TripNotFoundException();
    double allocatedWeight = trip.getRequests().stream().filter(r -> r.getStatus() == RequestStatus.ACCEPTED).mapToDouble(Request::getWeight).sum();
    return trip.getMaxWeight() - allocatedWeight;
  }

  public boolean canAcceptTrip(UUID id, double requestedWeight) throws Exception {
    Trip trip = getTripById(id);
    if (trip == null) throw new TripNotFoundException();
    return getAvailableWeight(id) >= requestedWeight;
  }

  // Freely editing a trip is the ability to totally edit a trip weight and price tp any value
  // This is only allowed when there is no accepted request to the trip
  // Origin, destination and departure date are in no way editable
  public boolean canFreelyEdit(UUID id) throws Exception {
    Trip trip = getTripById(id);
    if (trip == null) throw new TripNotFoundException();
    return trip.getRequests().stream().anyMatch(r -> r.getStatus() == RequestStatus.ACCEPTED);
  }

  //@PreAuthorize("tripRepository.findById(#id).orElse(null)?.traveller?.getUid == principal.username")
  public void editWeight(UUID id, double weight) throws Exception {
    Trip trip = getTripById(id);
    if (trip == null) throw new TripNotFoundException();
    if (!canFreelyEdit(id)) throw new CantEditTrip();
    trip.setMaxWeight(weight);
    tripRepository.save(trip);
  }

  //  @PreAuthorize("tripRepository.findById(#id).orElse(null)?.traveller?.getUid == principal.username")
  public void editPrice(UUID id, int price) throws Exception {
    Trip trip = getTripById(id);
    // todo: change weight when not freely
    if (trip == null) throw new TripNotFoundException();
    if (!canFreelyEdit(id)) throw new CantEditTrip();
    trip.setPrice(price);
    tripRepository.save(trip);
  }

}
