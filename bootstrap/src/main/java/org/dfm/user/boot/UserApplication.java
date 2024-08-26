package org.dfm.user.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.dfm.user")
public class UserApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
