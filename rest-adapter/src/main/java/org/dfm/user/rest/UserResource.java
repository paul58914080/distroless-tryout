package org.dfm.user.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.dfm.user.domain.model.User;
import org.dfm.user.domain.port.RequestUser;
import org.dfm.user.rest.generated.api.UserApi;
import org.dfm.user.rest.generated.model.UserInfo;

@RestController
public class UserResource implements UserApi {

  private final RequestUser requestUser;

  public UserResource(RequestUser requestUser) {
    this.requestUser = requestUser;
  }

  public ResponseEntity<UserInfo> getUsers() {
    return ResponseEntity.ok(UserInfo.builder().users(requestUser.getUsers()).build());
  }

  public ResponseEntity<User> getUserByCode(@PathVariable("code") Long code) {
    return ResponseEntity.ok(requestUser.getUserByCode(code));
  }
}
