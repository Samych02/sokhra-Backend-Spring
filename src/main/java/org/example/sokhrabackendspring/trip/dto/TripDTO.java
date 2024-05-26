package org.example.sokhrabackendspring.trip.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.example.sokhrabackendspring.trip.model.Place;

import java.sql.Date;

public class TripDTO {
  @Data
  @Builder
  public static class addTripDTO {
    @NotBlank
    private Place origin;

    @NotBlank
    private Place destination;

    @NotBlank
    private Date departureDate;

    @NotBlank
    private double weight;

    @NotBlank
    private int price;

  }
}
