package org.example.sokhrabackendspring.shipment.service;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.shipment.entity.Shipment;
import org.example.sokhrabackendspring.shipment.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {
  private final ShipmentRepository shipmentRepository;

  public List<Shipment> getAllShipments() {
    return shipmentRepository.findAll();
  }

  public Shipment getShipmentById(UUID id) {
    return shipmentRepository.findById(id).orElse(null);
  }

  public Shipment saveShipment(Shipment shipment) {
    return shipmentRepository.save(shipment);
  }
}
