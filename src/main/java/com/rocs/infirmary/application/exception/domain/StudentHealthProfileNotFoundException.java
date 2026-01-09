package com.rocs.infirmary.application.exception.domain;
/**
 * This exception is thrown when a student's health profile is not found.
 */
public class StudentHealthProfileNotFoundException extends RuntimeException{

    /**
     * Creates a new StudentHealthProfileNotFoundException with a specified message.
     *
     * @param message is the detail message that explains the reason for this exception
     */
    public StudentHealthProfileNotFoundException (String message) {
        super(message);
    }
}