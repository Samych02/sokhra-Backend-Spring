package org.example.sokhrabackendspring.user.repository;

import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
  Boolean existsByUid(String uid);

  @Query("SELECT profilePicture FROM User WHERE uid = :uid")
  String getProfilePictureByUid(@Param("uid") String uid);

}
