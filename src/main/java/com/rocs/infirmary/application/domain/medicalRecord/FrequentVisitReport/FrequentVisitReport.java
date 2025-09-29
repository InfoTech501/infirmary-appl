package com.rocs.infirmary.application.domain.medicalRecord.FrequentVisitReport;

import lombok.Data;
import java.util.Date;

/**
 * {@code FrequentVisitReport} represents a template for generating frequent visit reports,
 * capturing student details, visit frequency, and the reporting period.
 */
@Data
public class FrequentVisitReport {
    private Integer rank;
    private String fullName;
    private Integer age;
    private String section;
    private Long noOfVisit;

}

