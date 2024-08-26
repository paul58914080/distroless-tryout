package org.dfm.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.dfm.user.domain.UserDomain;
import org.dfm.user.domain.exception.UserNotFoundException;
import org.dfm.user.domain.model.User;
import org.dfm.user.domain.port.ObtainUser;

@ExtendWith(MockitoExtension.class)
public class AcceptanceTest {

  @Test
  @DisplayName("should be able to get users when asked for users from hard coded users")
  public void getUsersFromHardCoded() {
    /*
       RequestUser    - left side port
       UserDomain     - hexagon (domain)
       ObtainUser     - right side port
    */
    var requestUser = new UserDomain(); // the user is hard coded
    var users = requestUser.getUsers();
    assertThat(users)
        .hasSize(1)
        .extracting("description")
        .contains(
            "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
  }

  @Test
  @DisplayName("should be able to get users when asked for users from stub")
  public void getUsersFromMockedStub(@Mock ObtainUser obtainUser) {
    // Stub
    var user =
        User.builder()
            .code(1L)
            .description(
                "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
            .build();
    Mockito.lenient().when(obtainUser.getAllUsers()).thenReturn(List.of(user));
    // hexagon
    var requestUser = new UserDomain(obtainUser);
    var users = requestUser.getUsers();
    assertThat(users)
        .hasSize(1)
        .extracting("description")
        .contains(
            "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
  }

  @Test
  @DisplayName("should be able to get user when asked for user by id from stub")
  public void getUserByIdFromMockedStub(@Mock ObtainUser obtainUser) {
    // Given
    // Stub
    var code = 1L;
    var description =
        "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
    var expectedUser = User.builder().code(code).description(description).build();
    Mockito.lenient()
        .when(obtainUser.getUserByCode(code))
        .thenReturn(Optional.of(expectedUser));
    // When
    var requestUser = new UserDomain(obtainUser);
    var actualUser = requestUser.getUserByCode(code);
    assertThat(actualUser).isNotNull().isEqualTo(expectedUser);
  }

  @Test
  @DisplayName("should throw exception when asked for user by id that does not exists from stub")
  public void getExceptionWhenAskedUserByIdThatDoesNotExist(@Mock ObtainUser obtainUser) {
    // Given
    // Stub
    var code = -1000L;
    Mockito.lenient().when(obtainUser.getUserByCode(code)).thenReturn(Optional.empty());
    // When
    var requestUser = new UserDomain(obtainUser);
    // Then
    assertThatThrownBy(() -> requestUser.getUserByCode(code))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("User with code " + code + " does not exist");
  }
}
