package org.example.sokhrabackendspring.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {
  @Id
  private String uid;

  private String phoneNumber;

  private String firstName;

  private String lastName;

  private String profilePicture;

  public User(String uid) {
    this.uid = uid;
  }
}
