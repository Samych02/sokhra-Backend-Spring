package org.example.sokhrabackendspring.trip.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.sokhrabackendspring.common.entity.BaseEntity;
import org.example.sokhrabackendspring.shipment.entity.Shipment;
import org.example.sokhrabackendspring.trip.model.Place;
import org.example.sokhrabackendspring.trip.model.TripStatus;
import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trips")
@EntityListeners(AuditingEntityListener.class)
public class Trip extends BaseEntity {
  @ManyToOne
  @JoinColumn(nullable = false)
  private User traveller;

  @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Shipment> shipments = new ArrayList<>();

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

  private LocalDate departureDate;

  private double maxWeight;

  private Integer price;

  @Enumerated(EnumType.STRING)
  private TripStatus status;

}
