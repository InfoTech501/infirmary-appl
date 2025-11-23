package com.rocs.infirmary.application.exception.domain;

/**
 * Thrown when an error occurs during frequent visit report operations.
 */
public class FrequentVisitReportException extends RuntimeException {
    public FrequentVisitReportException(String message) {
        super(message);
    }
}