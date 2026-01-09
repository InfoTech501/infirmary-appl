package com.rocs.infirmary.application.exception.domain;
/**
 * this handles the exception for employee number exist exception
 * */
public class EmployeeNumberExistException extends RuntimeException {
    /**
     * this creates a constructor for {@code EmployeeNumberExistException}
     *
     * @param message contains the message about the exception
     * */
    public EmployeeNumberExistException(String message) {
        super(message);
    }
}
