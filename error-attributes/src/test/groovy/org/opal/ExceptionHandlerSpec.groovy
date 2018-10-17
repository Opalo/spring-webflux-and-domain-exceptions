package org.opal


import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static jodd.net.HttpMethod.POST
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.*

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SampleApp)
class ExceptionHandlerSpec extends Specification implements HTTPHelper {

  @LocalServerPort
  int port

  def 'should return 400 and error according to bean validation'() {
    when:
    def response = request().path('/samples').method(POST).body(toJson([name: null])).send()

    then:
    response.statusCode() == BAD_REQUEST.value()

    and:
    printJSON(response)

    and:
    def content = fromJSON(response)
    content.size() == 7
    content.timestamp
    content.status == BAD_REQUEST.value()
    content.error == BAD_REQUEST.reasonPhrase
    content.errors.size() == 1
    content.errors[0].field == 'name'
    content.errors[0].defaultMessage == 'must not be null'
    content.message.startsWith('Validation failed for argument at index')
    content.path == '/samples'
    content.traceId
  }

  def 'should return 500 on instance of RuntimeException'() {
    when:
    def response = request().method(POST).body(toJson([name: 'John'])).send()

    then:
    printJSON(response)

    and:
    response.statusCode() == INTERNAL_SERVER_ERROR.value()

    and:
    def content = fromJSON(response)
    content.size() == 6
    content.timestamp
    content.status == INTERNAL_SERVER_ERROR.value()
    content.error == INTERNAL_SERVER_ERROR.reasonPhrase
    content.message == 'Runtime John!'
    content.path == '/'
    content.traceId
  }

  def 'should return 418 on instance of ResponseStatusException'() {
    when:
    def response = request().method(POST).body(toJson([name: 'Mike'])).send()

    then:
    printJSON(response)

    and:
    response.statusCode() == I_AM_A_TEAPOT.value()

    and:
    def content = fromJSON(response)
    content.size() == 6
    content.timestamp
    content.status == I_AM_A_TEAPOT.value()
    content.error == I_AM_A_TEAPOT.reasonPhrase
    content.message == 'Teapot Mike!'
    content.path == '/'
    content.traceId
  }

  def 'should return 422 on annotated with @ResponseStatus exception'() {
    when:
    def response = request().method(POST).body(toJson([name: 'Tom'])).send()

    then:
    response.statusCode() == UNPROCESSABLE_ENTITY.value()

    and:
    def content = fromJSON(response)
    content.size() == 6
    content.timestamp
    content.status == UNPROCESSABLE_ENTITY.value()
    content.error == UNPROCESSABLE_ENTITY.reasonPhrase
    content.message == 'Unprocessable Tom!'
    content.path == '/'
    content.traceId
  }

  def 'should return 413 on handling via @ExceptionHandler'() {
    when:
    def response = request().method(POST).body(toJson([name: 'Joey'])).send()

    then:
    printJSON(response)

    and:
    response.statusCode() == PAYLOAD_TOO_LARGE.value()

    and:
    def content = fromJSON(response)
    content.size() == 6
    content.timestamp
    content.status == PAYLOAD_TOO_LARGE.value()
    content.error == PAYLOAD_TOO_LARGE.reasonPhrase
    content.message == 'Too large Joey!'
    content.path == '/'
    content.traceId
  }

}
