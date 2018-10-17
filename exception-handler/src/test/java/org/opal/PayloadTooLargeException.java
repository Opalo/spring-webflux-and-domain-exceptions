package org.opal;

public class PayloadTooLargeException extends RuntimeException {

  public PayloadTooLargeException() {
  }

  public PayloadTooLargeException(String message) {
    super(message);
  }

  public PayloadTooLargeException(String message, Throwable cause) {
    super(message, cause);
  }

  public PayloadTooLargeException(Throwable cause) {
    super(cause);
  }

  public PayloadTooLargeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
