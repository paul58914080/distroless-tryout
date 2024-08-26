package org.dfm.user.cucumber;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.dfm.user.boot.UserApplication;

@SpringBootTest(classes = UserApplication.class, webEnvironment = RANDOM_PORT)
@CucumberContextConfiguration
@ActiveProfiles("test")
public class SpringCucumberTestConfig {}
