package org.example.sokhrabackendspring.trip.repository;

import org.example.sokhrabackendspring.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {

}
