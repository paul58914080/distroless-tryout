package org.dfm.user.rest;

import org.dfm.user.domain.port.RequestUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.dfm.user")
public class UserRestAdapterApplication {

  @MockBean private RequestUser requestUser;

  public static void main(String[] args) {
    SpringApplication.run(UserRestAdapterApplication.class, args);
  }
}
