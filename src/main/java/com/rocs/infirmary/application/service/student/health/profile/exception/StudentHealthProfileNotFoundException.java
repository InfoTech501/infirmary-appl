package com.rocs.infirmary.application.service.student.health.profile.exception;

/**
 * StudentHealthProfileNotFoundException is a custom exception thrown when a specific
 * student is not found in the system.
 */
public class StudentHealthProfileNotFoundException extends RuntimeException {

    /**
     * Constructs StudentHealthProfileNotFoundException with the specified message.
     */
    public StudentHealthProfileNotFoundException(String message) {
        super(message);
    }


}