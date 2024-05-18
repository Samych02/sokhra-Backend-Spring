package org.example.sokhrabackendspring.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.sokhrabackendspring.user.dto.UserDTO;

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

  public User(String uid, String phoneNumber, UserDTO userDTO) {
    this.uid = uid;
    this.phoneNumber = phoneNumber;
    this.firstName = userDTO.getFirstName();
    this.lastName = userDTO.getLastName();
  }
}
