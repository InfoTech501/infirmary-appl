package com.rocs.infirmary.application.service.report.monthlyAilment;

import com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.request.MonthlyAilmentsReportRequestDto;
import com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.response.MonthlyAilmentsReportResponseDto;

public interface MonthlyAilmentReportService {
    /**
     * Generates a report of monthly ailments.
     *
     * @param request The request DTO containing the year, month, and an optional limit for the report.
     * @return A DTO containing the monthly ailments report.
     */
    MonthlyAilmentsReportResponseDto generateMonthlyAilmentsReport(MonthlyAilmentsReportRequestDto request);
}
