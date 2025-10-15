package com.rocs.infirmary.application.exception.domain;
/**
 * This exception is thrown when a specified section cannot be found.
 */
public class SectionNotFoundException extends Exception {
    /**
     * Creates a new SectionNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public SectionNotFoundException(String message) {
        super(message);
    }
}
