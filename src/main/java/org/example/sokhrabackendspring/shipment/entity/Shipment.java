package org.example.sokhrabackendspring.shipment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.example.sokhrabackendspring.common.entity.BaseEntity;
import org.example.sokhrabackendspring.shipment.model.RequestStatus;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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

  @NotEmpty
  private String title;

  private String note;

  @NotEmpty
  private Double weight;

  @NotEmpty
  private String shipmentPicture;

  @Enumerated(EnumType.STRING)
  private RequestStatus status;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

}
