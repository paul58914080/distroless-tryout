package org.dfm.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.dfm.user.domain.model.User;
import org.dfm.user.domain.port.ObtainUser;
import org.dfm.user.repository.dao.UserDao;
import org.dfm.user.repository.entity.UserEntity;

public class UserRepository implements ObtainUser {

  private final UserDao userDao;

  public UserRepository(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public List<User> getAllUsers() {
    return userDao.findAll().stream().map(UserEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Optional<User> getUserByCode(Long code) {
    var userEntity = userDao.findByCode(code);
    return userEntity.map(UserEntity::toModel);
  }
}
