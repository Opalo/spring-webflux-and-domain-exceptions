package org.opal


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler
import org.springframework.web.server.WebExceptionHandler
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static jodd.net.HttpMethod.POST
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

@SpringBootTest(
      webEnvironment = RANDOM_PORT,
      classes = [SampleApp, ExceptionHandlerConfiguration],
      properties = ['createWebfluxExceptionHandler=true']
)
class WebFluxResponseStatusExceptionHandlerSpec extends Specification implements HTTPHelper {

  @LocalServerPort
  int port

  def 'should return 422 on annotated with @ResponseStatus exception'() {
    when:
    def response = request().method(POST).body(toJson([name: 'Tom'])).send()

    then:
    response.statusCode() == UNPROCESSABLE_ENTITY.value()

    and:
    response.bodyBytes() == null
  }

}

@Configuration
class ExceptionHandlerConfiguration {

  @Bean
  @Order(-2)
  @ConditionalOnProperty("createWebfluxExceptionHandler")
  WebExceptionHandler webExceptionHandler() {
    new WebFluxResponseStatusExceptionHandler()
  }
  
}
