package org.example.sokhrabackendspring.user.repository;

import org.example.sokhrabackendspring.user.entity.Review;
import org.example.sokhrabackendspring.user.repository.projection.ReviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
  List<Review> findByReviewedId(String id);

  List<ReviewProjection> findAllProjectedByReviewedId(String id);
}