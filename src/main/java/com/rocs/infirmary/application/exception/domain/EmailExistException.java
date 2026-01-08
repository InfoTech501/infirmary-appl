package com.rocs.infirmary.application.exception.domain;
/**
 * {@code EmptyFieldException} is a custom exception used to handle empty fields.
 */

public class EmailExistException extends RuntimeException {
    /**
     * Creates a new {@code EmptyFieldException} with the specified detail message.
     *
     * @param message the detail message describing the cause of the exception
     */
    public EmailExistException(String message) {
        super(message);
    }
}
