package org.dfm.user.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.HookNoArgsBody;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.dfm.user.domain.model.User;
import org.dfm.user.repository.dao.UserDao;
import org.dfm.user.repository.entity.UserEntity;
import org.dfm.user.rest.generated.model.UserInfo;
import org.dfm.user.rest.generated.model.ProblemDetail;

public class UserStepDef implements En {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/users";
  @LocalServerPort private int port;
  private ResponseEntity responseEntity;

  public UserStepDef(TestRestTemplate restTemplate, UserDao userDao) {

    DataTableType(
        (Map<String, String> row) ->
            User.builder()
                .code(Long.parseLong(row.get("code")))
                .description(row.get("description"))
                .build());
    DataTableType(
        (Map<String, String> row) ->
            UserEntity.builder()
                .code(Long.parseLong(row.get("code")))
                .description(row.get("description"))
                .build());

    Before((HookNoArgsBody) userDao::deleteAll);
    After((HookNoArgsBody) userDao::deleteAll);

    Given(
        "the following users exists in the library",
        (DataTable dataTable) -> {
          List<UserEntity> poems = dataTable.asList(UserEntity.class);
          userDao.saveAll(poems);
        });

    When(
        "user requests for all users",
        () -> {
          String url = LOCALHOST + port + API_URI;
          responseEntity = restTemplate.getForEntity(url, UserInfo.class);
        });

    When(
        "user requests for users by code {string}",
        (String code) -> {
          String url = LOCALHOST + port + API_URI + "/" + code;
          responseEntity = restTemplate.getForEntity(url, User.class);
        });

    When(
        "user requests for users by id {string} that does not exists",
        (String code) -> {
          String url = LOCALHOST + port + API_URI + "/" + code;
          responseEntity = restTemplate.getForEntity(url, ProblemDetail.class);
        });

    Then(
        "the user gets an exception {string}",
        (String exception) -> {
          assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
          var actualResponse = (ProblemDetail) responseEntity.getBody();
          var expectedProblemDetail =
              ProblemDetail.builder()
                  .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
                  .status(HttpStatus.NOT_FOUND.value())
                  .detail("User with code 10000 does not exist")
                  .instance("/api/v1/users/10000")
                  .title("User not found")
                  .build();
          assertThat(actualResponse).isNotNull();
          assertThat(actualResponse)
              .usingRecursiveComparison()
              .ignoringFields("timestamp")
              .isEqualTo(expectedProblemDetail);
          assertThat(actualResponse.getTimestamp())
              .isCloseTo(LocalDateTime.now(), within(100L, ChronoUnit.SECONDS));
        });

    Then(
        "the user gets the following users",
        (DataTable dataTable) -> {
          List<User> expectedUsers = dataTable.asList(User.class);
          assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
          Object body = responseEntity.getBody();
          assertThat(body).isNotNull();
          if (body instanceof UserInfo) {
            assertThat(((UserInfo) body).getUsers())
                .isNotEmpty()
                .extracting("description")
                .containsAll(
                    expectedUsers.stream()
                        .map(User::getDescription)
                        .collect(Collectors.toList()));
          } else if (body instanceof User) {
            assertThat(body)
                .isNotNull()
                .extracting("description")
                .isEqualTo(expectedUsers.get(0).getDescription());
          }
        });
  }
}
