package org.example.sokhrabackendspring.trip.controller;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.requestresponse.util.ResponseUtil;
import org.example.sokhrabackendspring.trip.dto.TripDTO;
import org.example.sokhrabackendspring.trip.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class TripController {
  private final TripService tripService;

  @PostMapping("/trip/add")
  public ResponseEntity<?> addTrip(@AuthenticationPrincipal Jwt token,
                                   @ModelAttribute TripDTO.addTripDTO addTripDTO)
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
}
