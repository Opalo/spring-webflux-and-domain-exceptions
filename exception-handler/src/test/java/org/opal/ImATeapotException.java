package org.opal;

import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;

import org.springframework.web.server.ResponseStatusException;

class ImATeapotException extends ResponseStatusException {
  ImATeapotException() {
    this(null, null);
  }

  ImATeapotException(String reason) {
    super(I_AM_A_TEAPOT, reason, null);
  }

  ImATeapotException(String reason, Throwable cause) {
    super(I_AM_A_TEAPOT, reason, cause);
  }

}
