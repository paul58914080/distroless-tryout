package org.dfm.user.boot.config;

import org.dfm.user.domain.UserDomain;
import org.dfm.user.domain.port.ObtainUser;
import org.dfm.user.domain.port.RequestUser;
import org.dfm.user.repository.config.JpaAdapterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  public RequestUser getRequestUser(ObtainUser obtainUser) {
    return new UserDomain(obtainUser);
  }
}
