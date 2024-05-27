package org.example.sokhrabackendspring.trip.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.example.sokhrabackendspring.trip.model.Place;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class TripDTO {
  @Data
  @Builder
  public static class addTripDTO {
    @NotEmpty
    private Place origin;

    @NotEmpty
    private Place destination;

    @NotEmpty
    @DateTimeFormat
    private LocalDate departureDate;

    @NotEmpty
    @Min(0)
    private Integer weight;

    @NotEmpty
    @Min(0)
    private Integer price;

  }

  @Data
  @Builder
  public static class getTripsDTO {
    @NotEmpty
    private Integer page;

    @NotEmpty
    private Integer size;

    private Place origin;

    private Place destination;

    @DateTimeFormat
    private LocalDate departureDate;

    @Min(1)
    private Integer weight;

    @Min(0)
    private Integer price;

  }
}
