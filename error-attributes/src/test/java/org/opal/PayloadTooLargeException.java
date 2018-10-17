package org.opal;

import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;

class PayloadTooLargeException extends DomainException {
  PayloadTooLargeException(String message) {
    super(PAYLOAD_TOO_LARGE, message);
  }

  PayloadTooLargeException(String message, Throwable cause) {
    super(PAYLOAD_TOO_LARGE, message, cause);
  }
}
