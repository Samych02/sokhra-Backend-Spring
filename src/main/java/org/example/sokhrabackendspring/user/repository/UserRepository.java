package org.example.sokhrabackendspring.user.repository;

import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
  @Query("SELECT profilePicture FROM User WHERE id = :id")
  String getProfilePictureById(@Param("id") String id);

}
