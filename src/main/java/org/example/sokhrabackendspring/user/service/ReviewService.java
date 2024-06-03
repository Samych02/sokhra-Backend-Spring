package org.example.sokhrabackendspring.user.service;

import lombok.RequiredArgsConstructor;
import org.example.sokhrabackendspring.user.dto.ReviewDTO;
import org.example.sokhrabackendspring.user.entity.Review;
import org.example.sokhrabackendspring.user.entity.User;
import org.example.sokhrabackendspring.user.model.Rating;
import org.example.sokhrabackendspring.user.repository.ReviewRepository;
import org.example.sokhrabackendspring.user.repository.projection.ReviewProjection;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;

  public void castVote(Jwt token, ReviewDTO.CastVoteDTO castVoteDTO) {
    Review review = Review.builder()
            .caster(new User(token.getClaim("user_id")))
            .reviewed(new User(castVoteDTO.getReviewedID()))
            .rating(castVoteDTO.getRating())
            .comment(castVoteDTO.getComment())
            .build();
    reviewRepository.save(review);
  }

  public Rating getRating(String id) {
    List<Review> reviewList = reviewRepository.findByReviewedId(id);
    int numberOfRatings = reviewList.size();
    Double ratingValue = (numberOfRatings == 0) ? 0 : reviewList.stream().mapToDouble(Review::getRating).sum() / numberOfRatings;
    return new Rating(ratingValue, numberOfRatings);
  }

  public List<ReviewProjection> getAllUserReviews(String userId) {
    return reviewRepository.findAllProjectedByReviewedId(userId);
  }

}
