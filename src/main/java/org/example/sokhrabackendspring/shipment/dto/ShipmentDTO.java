package org.example.sokhrabackendspring.shipment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.example.sokhrabackendspring.shipment.validator.weightwithinlimitvalidator.WeightWithinLimit;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class ShipmentDTO {
  @Data
  @Builder
  @WeightWithinLimit
  public static class AddShipmentDTO {
    @NotNull
    private UUID tripID;

    @NotNull
    @Min(1)
    private Integer weight;

    @NotEmpty
    private String title;

    private String note;

    @NotNull
    private MultipartFile shipmentPicture;
  }


}
