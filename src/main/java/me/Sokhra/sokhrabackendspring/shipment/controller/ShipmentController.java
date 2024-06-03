package me.Sokhra.sokhrabackendspring.shipment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.Sokhra.sokhrabackendspring.requestresponse.util.ResponseUtil;
import me.Sokhra.sokhrabackendspring.shipment.dto.ShipmentDTO;
import me.Sokhra.sokhrabackendspring.shipment.service.ShipmentService;
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
public class ShipmentController {
  private final ShipmentService shipmentService;

  @PostMapping("/shipment/add")
  public ResponseEntity<?> addShipment(@AuthenticationPrincipal Jwt token,
                                       @ModelAttribute @Valid ShipmentDTO.AddShipmentDTO addShipmentDTO)
          throws IOException {
    System.out.println(addShipmentDTO);
    shipmentService.addShipment(token, addShipmentDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    ResponseUtil.successResponse(
                            "Shipment added successfully",
                            Collections.singletonMap("created", true)
                    )
            );
  }

}
