package org.example.sokhrabackendspring.trip.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.requestresponse.util.ResponseUtil;
import org.example.sokhrabackendspring.trip.dto.TripDTO;
import org.example.sokhrabackendspring.trip.model.Place;
import org.example.sokhrabackendspring.trip.repository.projection.TripProjection;
import org.example.sokhrabackendspring.trip.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class TripController {
  private final TripService tripService;

  @PostMapping("/trip/add")
  public ResponseEntity<?> addTrip(@AuthenticationPrincipal Jwt token,
                                   @RequestBody @Valid TripDTO.AddTripDTO addTripDTO) {
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
  public ResponseEntity<?> getAllTrips(@RequestParam(required = true) int page,
                                       @RequestParam(required = false, defaultValue = "10") int size,
                                       @RequestParam(required = false) String originCity,
                                       @RequestParam(required = false) String originCountry,
                                       @RequestParam(required = false) String destinationCity,
                                       @RequestParam(required = false) String destinationCountry,
                                       @RequestParam(required = false) @DateTimeFormat LocalDate departureDate,
                                       @RequestParam(required = false) @Min(1) Integer weight
  )
          throws Exception {
    TripDTO.GetTripsDTO getTripsDTO = TripDTO.GetTripsDTO.builder()
            .origin(new Place(originCity, originCountry))
            .destination(new Place(destinationCity, destinationCountry))
            .departureDate(departureDate)
            .weight(weight)
            .build();
    Page<TripProjection> tripPage = tripService.getTripsPaginated(page, size, getTripsDTO);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    ResponseUtil.successResponse(
                            "Trips fetched successfully",
                            Collections.singletonMap("tripPage", tripPage)
                    )
            );
  }
}
