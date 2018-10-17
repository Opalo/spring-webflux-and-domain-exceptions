package org.opal;

import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RequiredArgsConstructor
@RestController("/samples")
class SampleController {

  private final ReqTracer tracer;

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

  @ExceptionHandler({PayloadTooLargeException.class})
  ErrorResponse handleException(PayloadTooLargeException exc, ServerWebExchange exchange) {
    final var response = exchange.getResponse();
    response.setStatusCode(PAYLOAD_TOO_LARGE);
    final var request = exchange.getRequest();

    return new ErrorResponse(
      currentTimeMillis(),
      request.getPath().value(),
      PAYLOAD_TOO_LARGE.value(),
      PAYLOAD_TOO_LARGE.getReasonPhrase(),
      exc.getMessage(),
      tracer.traceId()
    );
  }

}
