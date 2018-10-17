package org.opal

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import jodd.http.HttpRequest
import jodd.http.HttpResponse

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

trait HTTPHelper {

  HttpRequest request() {
    new HttpRequest()
          .host('localhost')
          .protocol('http')
          .port(port)
          .contentType(APPLICATION_JSON_VALUE)
  }

  abstract int getPort();

  Map fromJSON(HttpResponse response) {
    new JsonSlurper().parse(response.bodyBytes()) as Map
  }

  void printJSON(HttpResponse response) {
    println(JsonOutput.prettyPrint(response.body()))
  }

}
