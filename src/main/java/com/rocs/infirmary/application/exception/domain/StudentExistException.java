package com.rocs.infirmary.application.exception.domain;
/**
 * this handles the exception for student exist exception
 * */
public class StudentExistException extends RuntimeException {
    /**
     * this creates a constructor for {@code StudentExistException}
     *
     * @param message contains the message about the exception
     * */
    public StudentExistException(String message) {
        super(message);
    }
}
