package com.rocs.infirmary.application.exception.domain;
/**
 * Exception thrown when a Student Already Exist
 */
public class StudentAlreadyExistException extends RuntimeException {
    /**
     * Constructs a new StudentAlreadyExistException with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public StudentAlreadyExistException(String message) {
        super(message);
    }
}
