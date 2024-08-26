package org.dfm.user.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.dfm.user.domain.exception.UserNotFoundException;
import org.dfm.user.domain.model.User;
import org.dfm.user.domain.port.RequestUser;
import org.dfm.user.rest.generated.model.ProblemDetail;
import org.dfm.user.rest.generated.model.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UserRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class UserResourceTest {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/users";
  @LocalServerPort private int port;
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private RequestUser requestUser;

  @Test
  @DisplayName("should start the rest adapter application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("should give users when asked for users with the support of domain stub")
  public void obtainUsersFromDomainStub() {
    // Given
    var user = User.builder().code(1L).description("Johnny Johnny Yes Papa !!").build();
    Mockito.lenient().when(requestUser.getUsers()).thenReturn(List.of(user));
    // When
    var url = LOCALHOST + port + API_URI;
    var responseEntity = restTemplate.getForEntity(url, UserInfo.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getUsers())
        .isNotEmpty()
        .extracting("description")
        .contains("Johnny Johnny Yes Papa !!");
  }

  @Test
  @DisplayName(
      "should give the user when asked for an user by code with the support of domain stub")
  public void obtainUserByCodeFromDomainStub() {
    // Given
    var code = 1L;
    var description = "Johnny Johnny Yes Papa !!";
    var user = User.builder().code(code).description(description).build();
    Mockito.lenient().when(requestUser.getUserByCode(code)).thenReturn(user);
    // When
    var url = LOCALHOST + port + API_URI + "/" + code;
    var responseEntity = restTemplate.getForEntity(url, User.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(user);
  }

  @Test
  @DisplayName(
      "should give exception when asked for an user by code that does not exists with the support of domain stub")
  public void shouldGiveExceptionWhenAskedForAnUserByCodeFromDomainStub() {
    // Given
    var code = -1000L;
    Mockito.lenient()
        .when(requestUser.getUserByCode(code))
        .thenThrow(new UserNotFoundException(code));
    // When
    var url = LOCALHOST + port + API_URI + "/" + code;
    var responseEntity = restTemplate.getForEntity(url, ProblemDetail.class);
    // Then
    var expectedProblemDetail =
        ProblemDetail.builder()
            .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
            .status(HttpStatus.NOT_FOUND.value())
            .detail("User with code -1000 does not exist")
            .instance("/api/v1/users/-1000")
            .title("User not found")
            .build();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody())
        .usingRecursiveComparison()
        .ignoringFields("timestamp")
        .isEqualTo(expectedProblemDetail);
    assertThat(responseEntity.getBody().getTimestamp())
        .isCloseTo(LocalDateTime.now(), within(100L, ChronoUnit.SECONDS));
  }
}
