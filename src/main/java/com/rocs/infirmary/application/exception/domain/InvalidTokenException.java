package com.rocs.infirmary.application.exception.domain;
/**
 * this handles the exception for invalid token
 * */
public class InvalidTokenException extends RuntimeException {
    /**
     * this creates a constructor for {@code InvalidTokenException}
     *
     * @param message contains the message about the exception
     * */
    public InvalidTokenException(String message) {
        super(message);
    }
}
