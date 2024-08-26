package org.dfm.user.domain.port;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.dfm.user.domain.model.User;

public interface ObtainUser {

  default List<User> getAllUsers() {
    User user =
        User.builder()
            .code(1L)
            .description(
                "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
            .build();
    return List.of(user);
  }

  default Optional<User> getUserByCode(@NonNull Long code) {
    return Optional.of(
        User.builder()
            .code(1L)
            .description(
                "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
            .build());
  }
}
