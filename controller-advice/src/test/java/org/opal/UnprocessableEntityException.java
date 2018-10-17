package org.opal;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

class UnprocessableEntityException extends DomainException {
  UnprocessableEntityException(String message) {
    super(UNPROCESSABLE_ENTITY, message);
  }

  UnprocessableEntityException(String message, Throwable cause) {
    super(UNPROCESSABLE_ENTITY, message, cause);
  }
}
