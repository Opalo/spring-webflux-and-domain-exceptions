package org.opal;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(UNPROCESSABLE_ENTITY)
class UnprocessableEntityException extends RuntimeException {
  UnprocessableEntityException() {
  }

  UnprocessableEntityException(String message) {
    super(message);
  }

  UnprocessableEntityException(String message, Throwable cause) {
    super(message, cause);
  }

  UnprocessableEntityException(Throwable cause) {
    super(cause);
  }

  UnprocessableEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
