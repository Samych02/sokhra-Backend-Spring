package org.example.sokhrabackendspring.shipment.service;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.example.sokhrabackendspring.imageutility.service.ImageService;
import org.example.sokhrabackendspring.shipment.dto.ShipmentDTO;
import org.example.sokhrabackendspring.shipment.entity.Shipment;
import org.example.sokhrabackendspring.shipment.model.ShipmentStatus;
import org.example.sokhrabackendspring.shipment.repository.ShipmentRepository;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.trip.service.TripService;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ShipmentService {
  private final ShipmentRepository shipmentRepository;
  private final ImageService imageService;

  public ShipmentService(ShipmentRepository shipmentRepository, @Qualifier("shipmentImageService") ImageService imageService, TripService tripService) {
    this.shipmentRepository = shipmentRepository;
    this.imageService = imageService;
  }

  public byte[] getShipmentPicture(UUID id) throws IOException {
    String shipmentPicture = shipmentRepository.getShipmentPictureById(id);
    return imageService.loadImage(shipmentPicture);
  }

  public void saveShipmentPicture(MultipartFile shipmentPicture, String id) throws IOException {
    imageService.saveImage(shipmentPicture, id);
  }

  public Shipment getShipmentById(UUID shipmentId) {
    return shipmentRepository.findById(shipmentId).orElse(null);
  }

  @SneakyThrows
  public void addShipment(Jwt token, ShipmentDTO.AddShipmentDTO addShipmentDTO) {
//    if (!tripService.canAcceptTrip(addShipmentDTO.getTripID(), addShipmentDTO.getWeight()))
//      throw new ExceedsAvailableWeight();
    User sender = new User(token.getClaim("user_id"));
    Trip trip = new Trip(addShipmentDTO.getTripID());

    Shipment shipment = Shipment.builder().sender(sender).trip(trip).title(addShipmentDTO.getTitle()).note(addShipmentDTO.getNote()).weight(addShipmentDTO.getWeight()).shipmentPicture(UUID.randomUUID().toString() + "." + Objects.requireNonNull(FilenameUtils.getExtension(addShipmentDTO.getShipmentPicture().getOriginalFilename())).toLowerCase()).status(ShipmentStatus.PENDING).build();
    saveShipmentPicture(addShipmentDTO.getShipmentPicture(), shipment.getShipmentPicture());
    shipmentRepository.save(shipment);
  }

  public Integer getShipmentsCountByUserId(String id) {
    return shipmentRepository.countAllBySenderIdAndStatus(id, ShipmentStatus.DELIVERED);
  }
}
