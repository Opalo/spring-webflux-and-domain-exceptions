package org.opal;

import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;

class ImATeapotException extends DomainException {
  ImATeapotException(String message) {
    super(I_AM_A_TEAPOT, message);
  }

  ImATeapotException(String message, Throwable cause) {
    super(I_AM_A_TEAPOT, message, cause);
  }
}
