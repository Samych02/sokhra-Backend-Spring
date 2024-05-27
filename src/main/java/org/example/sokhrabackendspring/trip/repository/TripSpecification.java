package org.example.sokhrabackendspring.trip.repository;

import jakarta.persistence.criteria.*;
import org.example.sokhrabackendspring.shipment.entity.Shipment;
import org.example.sokhrabackendspring.trip.dto.TripDTO;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class TripSpecification {
  public static Specification<Trip> getTripsByParams(TripDTO.getTripsDTO getTripsDTO) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (getTripsDTO.getOrigin() != null) {
        predicates.add(criteriaBuilder.equal(root.get("origin"), getTripsDTO.getOrigin()));
      }

      if (getTripsDTO.getDestination() != null) {
        predicates.add(criteriaBuilder.equal(root.get("destination"), getTripsDTO.getDestination()));
      }

      if (getTripsDTO.getDepartureDate() != null) {
        predicates.add(criteriaBuilder.equal(root.get("departureDate"), getTripsDTO.getDepartureDate()));
      }

      if (getTripsDTO.getPrice() != null) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), getTripsDTO.getPrice()));
      }


      // Join shipments to trip
      Join<Trip, Shipment> shipmentsJoin = root.join("shipments", JoinType.LEFT);

      // Subquery to calculate the sum of shipment weights for each trip
      Subquery<Double> shipmentWeightSubquery = query.subquery(Double.class);
      Root<Shipment> shipmentRoot = shipmentWeightSubquery.from(Shipment.class);
      shipmentWeightSubquery.select(criteriaBuilder.sum(shipmentRoot.get("weight")));
      shipmentWeightSubquery.where(criteriaBuilder.equal(shipmentRoot.get("trip"), root.get("id")));

      //todo: add status

      // Return combined predicates
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
