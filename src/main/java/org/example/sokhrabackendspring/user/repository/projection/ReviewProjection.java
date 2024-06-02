package org.example.sokhrabackendspring.user.repository.projection;

import java.time.LocalDateTime;

public interface ReviewProjection {
  ReviewerProjection getCaster();

  Integer getRating();

  String getComment();

  LocalDateTime getCreatedAt();
}
