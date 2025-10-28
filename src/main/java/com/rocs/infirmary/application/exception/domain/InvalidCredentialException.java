package com.rocs.infirmary.application.exception.domain;
/**
 * this handles the exception for invalid credentials
 * */
public class InvalidCredentialException extends RuntimeException {
    /**
     * this creates a constructor for {@code InvalidCredentialException}
     *
     * @param message contains the message about the exception
     * */
    public InvalidCredentialException(String message) {
        super(message);
    }
}
