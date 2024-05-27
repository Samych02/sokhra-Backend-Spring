package org.example.sokhrabackendspring.shipment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.example.sokhrabackendspring.trip.model.Place;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

public class ShipmentDTO {
  @Data
  @Builder
  public static class addShipmentDTO {
    @NotEmpty
    private UUID tripID;

    @NotEmpty
    @Min(1)
    private Integer weight;

    @NotEmpty
    private String title;

    private String note;

    @NotEmpty
    private MultipartFile shipmentPicture;
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

    private LocalDate departureDate;

    private Double weight;

    private Integer price;

  }
}
