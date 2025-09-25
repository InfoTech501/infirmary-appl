package com.rocs.infirmary.application.service.student.profile.exceptions;

/*
 *MedicalHistoryNotFoundException is a custom exception thrown when a student's
 * medical history is not found.
 */
public class MedicalHistoryNotFoundException extends RuntimeException{


    /*
     * Constructs MedicalHistoryNotFoundException with the specified message.
     */
    public MedicalHistoryNotFoundException(String message){
        super(message);
    }
}