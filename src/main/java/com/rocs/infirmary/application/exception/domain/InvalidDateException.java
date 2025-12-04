package com.rocs.infirmary.application.exception.domain;

public class InvalidDateException extends RuntimeException {
    /**
     * this creates a constructor for {@code InvalidCredentialException}
     *
     * @param message contains the message about the exception
     * */
    public InvalidDateException(String message) {
        super(message);
    }
}
