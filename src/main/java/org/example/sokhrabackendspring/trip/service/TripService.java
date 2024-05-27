package org.example.sokhrabackendspring.trip.service;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.shipment.entity.Shipment;
import org.example.sokhrabackendspring.shipment.model.ShipmentStatus;
import org.example.sokhrabackendspring.trip.dto.TripDTO;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.trip.exception.TripNotFoundException;
import org.example.sokhrabackendspring.trip.model.TripStatus;
import org.example.sokhrabackendspring.trip.repository.TripRepository;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.example.sokhrabackendspring.trip.repository.TripSpecification.getTripsByParams;

@Service
@RequiredArgsConstructor
public class TripService {
  private final TripRepository tripRepository;

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
            .departureDate(addTripDTO.getDepartureDate())
            .status(TripStatus.ACTIVE)
            .build();
    tripRepository.save(trip);
  }

  public Integer getAvailableWeight(UUID id) throws Exception {
    Trip trip = getTripById(id);
    if (trip == null) throw new TripNotFoundException();
    Integer allocatedWeight = trip.getShipments().stream()
            .filter(r -> r.getStatus() == ShipmentStatus.ACCEPTED || r.getStatus() == ShipmentStatus.PENDING)
            .mapToInt(Shipment::getWeight).sum();
    return trip.getMaxWeight() - allocatedWeight;
  }

  public boolean canAcceptTrip(UUID id, Integer requestedWeight) throws Exception {
    Trip trip = getTripById(id);
    if (trip == null) throw new TripNotFoundException();
    return getAvailableWeight(id) >= requestedWeight;
  }

  public Page<Trip> getTripsPaginated(TripDTO.getTripsDTO getTripsDTO) {
    Pageable pageable = PageRequest.of(getTripsDTO.getPage(), getTripsDTO.getSize(), Sort.by("createdAt").descending());
    Specification<Trip> specification = getTripsByParams(getTripsDTO);
    System.out.println(69);
    System.out.println(tripRepository.findAll(specification));
    return tripRepository.findAll(specification, pageable);
  }

  // Freely editing a trip is the ability to totally edit a trip weight and price tp any value
  // This is only allowed when there is no accepted request to the trip
  // Origin, destination and departure date are in no way editable
//  public boolean canFreelyEdit(UUID id) throws Exception {
//    Trip trip = getTripById(id);
//    if (trip == null) throw new TripNotFoundException();
//    return trip.getShipments().stream().anyMatch(r -> r.getStatus() == RequestStatus.ACCEPTED);
//  }

  //@PreAuthorize("tripRepository.findById(#id).orElse(null)?.traveller?.getUid == principal.username")
//  public void editWeight(UUID id, Double weight) throws Exception {
//    Trip trip = getTripById(id);
//    if (trip == null) throw new TripNotFoundException();
//    if (!canFreelyEdit(id)) throw new CantEditTrip();
//    trip.setMaxWeight(weight);
//    tripRepository.save(trip);
//  }

//  //  @PreAuthorize("tripRepository.findById(#id).orElse(null)?.traveller?.getUid == principal.username")
//  public void editPrice(UUID id, Integer price) throws Exception {
//    Trip trip = getTripById(id);
//    // todo: change weight when not freely
//    if (trip == null) throw new TripNotFoundException();
//    if (!canFreelyEdit(id)) throw new CantEditTrip();
//    trip.setPrice(price);
//    tripRepository.save(trip);
//  }

}
