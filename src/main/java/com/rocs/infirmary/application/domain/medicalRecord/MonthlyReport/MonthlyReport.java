package com.rocs.infirmary.application.domain.medicalRecord.MonthlyReport;

import lombok.Data;

@Data
public class MonthlyReport {
    private Integer rank;
    private String illness;
    private Long noOfStudents;
}
