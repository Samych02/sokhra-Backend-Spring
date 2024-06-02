package org.example.sokhrabackendspring.user.repository.projection;

public interface ReviewProjection {
  ReviewerProjection getCaster();

  Integer getRating();

  String getComment();
}
