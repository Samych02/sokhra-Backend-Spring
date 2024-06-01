package org.example.sokhrabackendspring.trip.repository;

import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.trip.model.TripStatus;
import org.example.sokhrabackendspring.trip.repository.projection.TripProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID>, JpaSpecificationExecutor<Trip> {
  Page<TripProjection> findAllByStatus(TripStatus status, Pageable pageable);

  @Query("""
          SELECT trip FROM Trip trip WHERE
          ((:originCity IS NULL OR trip.origin.city = :originCity) AND (:originCountry IS NULL OR trip.origin.country = :originCountry))
          AND
          ((:destinationCity IS NULL OR trip.destination.city = :destinationCity) AND (:destinationCountry IS NULL OR trip.destination.country = :destinationCountry))
          AND
          (:departureDate IS NULL OR trip.departureDate = :departureDate)
          AND
          (:weight IS NULL OR (COALESCE((SELECT SUM(shipments.weight) FROM trip.shipments shipments), 0)) <= trip.maxWeight - :weight)
          AND
          (trip.status = :status)
          """)
  Page<TripProjection> findAllBy(@Param("originCity") String originCity, @Param("originCountry") String originCountry,
                                 @Param("destinationCity") String destinationCity, @Param("destinationCountry") String destinationCountry,
                                 @Param("departureDate") LocalDate departureDate,
                                 @Param("weight") Integer weight,
                                 @Param("status") TripStatus status,
                                 Pageable pageable);
}
