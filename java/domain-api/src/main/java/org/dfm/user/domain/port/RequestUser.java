package org.dfm.user.domain.port;

import java.util.List;
import lombok.NonNull;
import org.dfm.user.domain.model.User;

public interface RequestUser {

  List<User> getUsers();

  User getUserByCode(@NonNull Long code);
}
