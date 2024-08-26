package org.dfm.user.domain.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long id) {
    super("User with code " + id + " does not exist");
  }
}
