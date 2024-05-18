package org.example.sokhrabackendspring.user.repository;

import org.example.sokhrabackendspring.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  Boolean existsByUid(String uid);

}
