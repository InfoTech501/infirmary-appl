package com.rocs.infirmary.application.exception.domain;

/**
 * Thrown exceptions when error occurs during monthly ailment report operations.
 */
public class MonthlyAilmentReportException extends RuntimeException {
  /**
   * Constructs an exception with the specified message and cause.
   * @param message the detail message
   * @param cause the cause of this exception
   */
  public MonthlyAilmentReportException(String message, Throwable cause) {
    super(message, cause);
  }
}
