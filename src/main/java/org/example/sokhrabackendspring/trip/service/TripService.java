package org.example.sokhrabackendspring.trip.service;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.shipment.entity.Shipment;
import org.example.sokhrabackendspring.shipment.model.ShipmentStatus;
import org.example.sokhrabackendspring.trip.dto.TripDTO;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.trip.exception.TripNotFoundException;
import org.example.sokhrabackendspring.trip.model.TripStatus;
import org.example.sokhrabackendspring.trip.repository.TripRepository;
import org.example.sokhrabackendspring.trip.repository.projection.TripProjection;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TripService {
  private final TripRepository tripRepository;
  private final WebInvocationPrivilegeEvaluator privilegeEvaluator;

  public Trip getTripById(UUID id) {
    return tripRepository.findById(id).orElse(null);
  }

  public void addTrip(Jwt token, TripDTO.AddTripDTO addTripDTO) {
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

  public Page<TripProjection> getTripsPaginated(int page, int size, TripDTO.GetTripsDTO getTripsDTO) {
    Pageable pageable;
    if (getTripsDTO == null || getTripsDTO.areAllFieldsNull()) {
      System.out.println(6969);
      pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
      return tripRepository.findAllByStatus(TripStatus.ACTIVE, pageable);
    }
    pageable = PageRequest.of(page, size, Sort.by("price").ascending());
//    getTripsDTO.parse();
//    if (getTripsDTO.getDepartureDate() == null)
//      return tripRepository.findAllByOriginAndDestination(getTripsDTO, pageable).stream()
//              .filter(tripProjection -> tripProjection.getAvailableWeight() >= getTripsDTO.getWeight()).collect(Collectors.collectingAndThen(Collectors.toList(),
//                      list -> new PageImpl<>(list, pageable, list.size())));
//
//    return tripRepository.findAllByOriginAndDestinationAndDepartureDate(getTripsDTO, pageable);
    return tripRepository.findAllBy((getTripsDTO.getOrigin().getCity() == null) ? null : getTripsDTO.getOrigin().getCity().toLowerCase(), (getTripsDTO.getOrigin().getCity() == null) ? null : getTripsDTO.getOrigin().getCountry().toLowerCase(),
            (getTripsDTO.getDestination().getCity() == null) ? null : getTripsDTO.getDestination().getCity().toLowerCase(), (getTripsDTO.getDestination().getCity() == null) ? null : getTripsDTO.getDestination().getCountry().toLowerCase(),
            (getTripsDTO.getDepartureDate() == null) ? null : getTripsDTO.getDepartureDate(),
            (getTripsDTO.getWeight() == null) ? null : getTripsDTO.getWeight(),
            TripStatus.ACTIVE,
            pageable);
  }

//  public Page<Trip> getTripsPaginated(int page, int size, TripDTO.GetTripsDTO getTripsDTO) {
//    Pageable pageable = PageRequest.of(getTripsDTO.getPage(), getTripsDTO.getSize());
//    return tripRepository.findAll(pageable);
//  }

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
