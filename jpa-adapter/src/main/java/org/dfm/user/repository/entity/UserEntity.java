package org.dfm.user.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.dfm.user.domain.model.User;

@Table(name = "T_USER")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_USER")
  @SequenceGenerator(
      name = "SEQ_T_USER",
      sequenceName = "SEQ_T_USER",
      allocationSize = 1,
      initialValue = 1)
  @Column(name = "TECH_ID")
  private Long techId;

  @Column(name = "CODE")
  private Long code;

  @Column(name = "DESCRIPTION")
  private String description;

  public User toModel() {
    return User.builder().code(code).description(description).build();
  }
}
