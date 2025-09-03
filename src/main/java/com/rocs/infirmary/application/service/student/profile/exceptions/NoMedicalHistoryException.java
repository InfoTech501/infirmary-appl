package com.rocs.infirmary.application.service.student.profile.exceptions;

public class NoMedicalHistoryException extends RuntimeException{

   public NoMedicalHistoryException(String message){
       super(message);
   }
}
