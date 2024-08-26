package org.dfm.user.repository.dao;

import java.util.Optional;
import org.dfm.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao
    extends JpaRepository<UserEntity, Long>, RevisionRepository<UserEntity, Long, Long> {

  Optional<UserEntity> findByCode(Long code);
}
