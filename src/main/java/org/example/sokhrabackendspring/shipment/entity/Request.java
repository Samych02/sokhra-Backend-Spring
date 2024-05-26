package org.example.sokhrabackendspring.shipment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.sokhrabackendspring.shipment.model.RequestStatus;
import org.example.sokhrabackendspring.trip.entity.Trip;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
@Builder
public class Request {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private User sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Trip trip;

  @NotEmpty
  private String title;

  private String note;

  @NotEmpty
  private double weight;

  @NotEmpty
  private String requestPicture;

  @Enumerated(EnumType.STRING)
  private RequestStatus status;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;


}
