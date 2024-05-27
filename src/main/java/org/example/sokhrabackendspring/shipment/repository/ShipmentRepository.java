package org.example.sokhrabackendspring.shipment.repository;

import org.example.sokhrabackendspring.shipment.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
  @Query("SELECT shipmentPicture FROM Shipment WHERE id = :id")
  String getShipmentPictureById(@Param("id") UUID id);
}
