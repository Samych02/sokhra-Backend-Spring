package org.example.sokhrabackendspring.trip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.sokhrabackendspring.shipment.entity.Request;
import org.example.sokhrabackendspring.trip.model.Place;
import org.example.sokhrabackendspring.trip.model.TripStatus;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trips")
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private User traveller;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "city", column = @Column(name = "origin_city")),
          @AttributeOverride(name = "country", column = @Column(name = "origin_country"))
  })
  private Place origin;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "city", column = @Column(name = "destination_city")),
          @AttributeOverride(name = "country", column = @Column(name = "destination_country"))
  })
  private Place destination;

  private Date departureDate;

  private double maxWeight;

  private int price;

  @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Request> requests = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private TripStatus status;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
