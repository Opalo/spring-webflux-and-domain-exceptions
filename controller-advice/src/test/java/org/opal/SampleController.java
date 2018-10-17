package org.opal;

import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/samples")
class SampleController {

  @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  @ResponseBody
  SampleResponse post(@Valid @RequestBody SampleRequest req) {
    var name = req.getName();
    switch (name) {
      case "John":
        throw new RuntimeException("Runtime John!");
      case "Mike":
        throw new ImATeapotException("Teapot Mike!");
      case "Tom":
        throw new UnprocessableEntityException("Unprocessable Tom!");
      case "Joey":
        throw new PayloadTooLargeException("Too large Joey!");
    }
    return new SampleResponse(randomUUID(), name);
  }

}
