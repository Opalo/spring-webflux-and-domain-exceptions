package org.opal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@SpringBootApplication
@RequiredArgsConstructor
class SampleApp implements WebFluxConfigurer {

  @Autowired
  private final Validator validator;

  public static void main(String[] args) {
    SpringApplication.run(SampleApp.class, args);
  }

  @Override
  public Validator getValidator() {
    return validator;
  }

}