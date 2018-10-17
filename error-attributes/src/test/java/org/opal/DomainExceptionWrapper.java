package org.opal;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Slf4j
@Component
class DomainExceptionWrapper extends DefaultErrorAttributes {

  private final ReqTracer tracer;

  DomainExceptionWrapper(ReqTracer tracer) {
    super(false);
    this.tracer = tracer;
  }

  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
    final var error = getError(request);
    final var errorAttributes = super.getErrorAttributes(request, false);
    errorAttributes.put(ErrorAttribute.TRACE_ID.value, tracer.traceId());
    if (error instanceof DomainException) {
      log.debug("Caught an instance of: {}, err: {}", DomainException.class, error);
      final var errorStatus = ((DomainException) error).getStatus();
      errorAttributes.replace(ErrorAttribute.STATUS.value, errorStatus.value());
      errorAttributes.replace(ErrorAttribute.ERROR.value, errorStatus.getReasonPhrase());
      return errorAttributes;
    }
    return errorAttributes;
  }


  enum ErrorAttribute {
    STATUS("status"),
    ERROR("error"),
    TRACE_ID("traceId");

    private final String value;

    ErrorAttribute(String value) {
      this.value = value;
    }
  }

}
