package com.rocs.infirmary.application.service.student.health.profile.exceptions;

/**
 * StudentNotFoundException is a custom exception thrown when a specific
 * student is not found in the system.
 */
public class StudentNotFoundException extends RuntimeException {

    /**
     * Constructs StudentNotFoundException with the specified message.
     */
    public StudentNotFoundException(String message) {
        super(message);
    }


}