package com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.response;

import com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.MonthlyAilmentReportDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for the monthly ailments report response.
 * This class encapsulates the complete report data, including the reporting period details,
 * the total number of records processed, and a list of individual ailment reports.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyAilmentsReportResponseDto {
    private Integer year;
    private Integer month;
    private String monthName;
    private Long totalRecords;
    private List<MonthlyAilmentReportDto> ailments;
}
