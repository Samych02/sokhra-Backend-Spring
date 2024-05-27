package org.example.sokhrabackendspring.trip.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.example.sokhrabackendspring.trip.model.Place;

import java.time.LocalDate;

public class TripDTO {
  @Data
  @Builder
  public static class addTripDTO {
    @NotBlank
    private Place origin;

    @NotBlank
    private Place destination;

    @NotBlank
    private LocalDate departureDate;

    @NotBlank
    private Double weight;

    @NotBlank
    private Integer price;

  }

  @Data
  @Builder
  public static class getTripsDTO {
    @NotBlank
    private Integer page;

    @NotBlank
    private Integer size;

    private Place origin;

    private Place destination;

    private LocalDate departureDate;

    private Double weight;

    private Integer price;

  }
}
