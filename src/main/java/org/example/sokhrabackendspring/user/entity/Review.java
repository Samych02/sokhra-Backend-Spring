package org.example.sokhrabackendspring.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.sokhrabackendspring.common.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
@Builder
public class Review extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private User reviewer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private User reviewed;

  private int rating;

  private String comment;
}
