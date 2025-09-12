package com.rocs.infirmary.application.domain.medicalRecord.MonthlyAilmentReport;

import lombok.Data;

@Data
public class MonthlyAilmentReport {
    private Integer rank;
    private String illness;
    private Long noOfStudents;
}
