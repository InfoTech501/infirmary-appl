package com.rocs.infirmary.application.exception.domain;
/**
 * this class is used to handle the exception when student is not found
 * */
public class StudentNotFoundException extends Exception {
    /**
     * this creates a constructor for {@code StudentNotFoundException}
     *
     * @param message is the message used when the program related to student data fetching throws an error
     * */
    public StudentNotFoundException(String message) {
        super(message);
    }
}
