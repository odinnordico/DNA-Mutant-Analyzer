package com.meli.test.dnamutantanalyzer.exception;

/**
 * AnalysisException is the custom exception managed across the system
 */
public class AnalysisException extends RuntimeException {

  public AnalysisException(String message) {
    super(message);
  }

  public AnalysisException(String message, Throwable cause) {
    super(message, cause);
  }
}
