package org.dfm.user.domain;

import java.util.List;
import lombok.NonNull;
import org.dfm.user.domain.exception.UserNotFoundException;
import org.dfm.user.domain.model.User;
import org.dfm.user.domain.port.ObtainUser;
import org.dfm.user.domain.port.RequestUser;

public class UserDomain implements RequestUser {

  private final ObtainUser obtainUser;

  public UserDomain() {
    this(new ObtainUser() {});
  }

  public UserDomain(ObtainUser obtainUser) {
    this.obtainUser = obtainUser;
  }

  @Override
  public List<User> getUsers() {
    return obtainUser.getAllUsers();
  }

  @Override
  public User getUserByCode(@NonNull Long code) {
    var user = obtainUser.getUserByCode(code);
    return user.orElseThrow(() -> new UserNotFoundException(code));
  }
}
