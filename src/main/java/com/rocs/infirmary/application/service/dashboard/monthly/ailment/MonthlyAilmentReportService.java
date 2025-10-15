package com.rocs.infirmary.application.service.dashboard.monthly.ailment;

import com.rocs.infirmary.application.domain.medical.MonthlyAilmentReport.MonthlyAilmentReport;

import java.util.List;

/**
 * Service interface for generating monthly reports on common ailments.
 * <p>
 * This interface defines the contract for generating reports that summarize
 * the frequency and distribution of ailments diagnosed in a given month and year.
 * Implementations are expected to query relevant medical records, aggregate data,
 * and return a structured result suitable for reporting or presentation.
 * </p>
 */
public interface MonthlyAilmentReportService {
    /**
     * Generates a monthly ailment report with ailment descriptions and student counts.
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return JSON-compatible list or a message map
     */
    List<MonthlyAilmentReport> generateMonthlyAilmentsReport(String startDate, String endDate);
}
