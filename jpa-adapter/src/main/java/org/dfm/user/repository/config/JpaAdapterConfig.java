package org.dfm.user.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.dfm.user.domain.port.ObtainUser;
import org.dfm.user.repository.UserRepository;
import org.dfm.user.repository.dao.UserDao;

@Configuration
@EntityScan("org.dfm.user.repository.entity")
@EnableJpaRepositories(
    basePackages = "org.dfm.user.repository.dao",
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class JpaAdapterConfig {

  @Bean
  public ObtainUser getUserRepository(UserDao userDao) {
    return new UserRepository(userDao);
  }
}
