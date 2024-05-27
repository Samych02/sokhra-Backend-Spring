package org.example.sokhrabackendspring.shipment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.sokhrabackendspring.common.entity.BaseEntity;
import org.example.sokhrabackendspring.shipment.model.ShipmentStatus;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipments")
@Builder
public class Shipment extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private User sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id")
  @JsonBackReference
  private Trip trip;

  private String title;

  private String note;

  private Integer weight;

  private String shipmentPicture;

  @Enumerated(EnumType.STRING)
  private ShipmentStatus status;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public Shipment(UUID id) {
    super(id);
  }

  public Shipment(UUID id, User sender, Trip trip, String title, String note, Integer weight, String shipmentPicture, ShipmentStatus status, LocalDateTime createdAt) {
    super(id);
    this.sender = sender;
    this.trip = trip;
    this.title = title;
    this.note = note;
    this.weight = weight;
    this.shipmentPicture = shipmentPicture;
    this.status = status;
    this.createdAt = createdAt;
  }
}