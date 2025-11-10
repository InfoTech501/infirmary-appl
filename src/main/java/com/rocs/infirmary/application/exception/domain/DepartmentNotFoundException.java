package com.rocs.infirmary.application.exception.domain;
/**
 * this handles the exception for department not found
 * */
public class DepartmentNotFoundException extends RuntimeException {
    /**
     * this creates a constructor for {@code DepartmentNotFoundException}
     *
     * @param message contains the message about the exception
     * */
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
