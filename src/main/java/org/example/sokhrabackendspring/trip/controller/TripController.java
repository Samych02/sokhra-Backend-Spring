package org.example.sokhrabackendspring.trip.controller;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.requestresponse.util.ResponseUtil;
import org.example.sokhrabackendspring.trip.dto.TripDTO;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.trip.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class TripController {
  private final TripService tripService;

  @PostMapping("/trip/add")
  public ResponseEntity<?> addTrip(@AuthenticationPrincipal Jwt token,
                                   @RequestBody TripDTO.addTripDTO addTripDTO)
          throws IOException {
    tripService.addTrip(token, addTripDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    ResponseUtil.successResponse(
                            "Trip added successfully",
                            Collections.singletonMap("created", true)
                    )
            );
  }

  @GetMapping("/trips")
  public ResponseEntity<?> getAllTrips(@RequestBody TripDTO.getTripsDTO getTripsDTO) throws Exception {
    Page<Trip> tripPage = tripService.getTripsPaginated(getTripsDTO);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    ResponseUtil.successResponse(
                            "Trips fetched successfully",
                            Collections.singletonMap("tripPage", tripPage)
                    )
            );

  }

  @GetMapping("/test")
  public Double test(@RequestBody TripDTO.getTripsDTO getTripsDTO) throws Exception {
    return tripService.getAvailableWeight(UUID.fromString("c4ef9fc6-f3d7-44b2-bf8f-d6a722a0e9ff"));

  }
}
