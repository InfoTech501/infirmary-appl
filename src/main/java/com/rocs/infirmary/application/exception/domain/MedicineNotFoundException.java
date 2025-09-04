package com.rocs.infirmary.application.exception.domain;

public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(String message) {
        super(message);
    }
}
