package com.rocs.infirmary.application.domain.medicalRecord.MonthlyAilmentReport;

import lombok.Data;

/**
 * {@code MonthlyAilmentReport} represents a template for generating monthly common ailments report.
 * */
@Data
public class MonthlyAilmentReport {
    private Integer rank;
    private String illness;
    private Long noOfStudents;
}
