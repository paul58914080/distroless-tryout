package org.dfm.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.dfm.user.domain.model.User;
import org.dfm.user.domain.port.ObtainUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserJpaTest {

  @Autowired private ObtainUser obtainUser;

  @Test
  @DisplayName("should start the application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given users exist in database when asked should return all users from database")
  public void shouldGiveMeUsersWhenAskedGivenUserExistsInDatabase() {
    // Given from @Sql
    // When
    var users = obtainUser.getAllUsers();
    // Then
    assertThat(users).isNotNull().extracting("description").contains("Twinkle twinkle little star");
  }

  @Test
  @DisplayName("given no users exists in database when asked should return empty")
  public void shouldGiveNoUserWhenAskedGivenUsersDoNotExistInDatabase() {
    // When
    var users = obtainUser.getAllUsers();
    // Then
    assertThat(users).isNotNull().isEmpty();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given users exists in database when asked for user by id should return the user")
  public void shouldGiveTheUserWhenAskedByIdGivenThatUserByThatIdExistsInDatabase() {
    // Given from @Sql
    // When
    var user = obtainUser.getUserByCode(1L);
    // Then
    assertThat(user)
        .isNotNull()
        .isNotEmpty()
        .get()
        .isEqualTo(User.builder().code(1L).description("Twinkle twinkle little star").build());
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName(
      "given users exists in database when asked for user by id that does not exist should give empty")
  public void shouldGiveNoUserWhenAskedByIdGivenThatUserByThatIdDoesNotExistInDatabase() {
    // Given from @Sql
    // When
    var user = obtainUser.getUserByCode(-1000L);
    // Then
    assertThat(user).isEmpty();
  }
}
