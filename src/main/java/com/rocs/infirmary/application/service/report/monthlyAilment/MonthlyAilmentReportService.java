package com.rocs.infirmary.application.service.report.monthlyAilment;

import com.rocs.infirmary.application.domain.medicalRecord.MonthlyAilmentReport.MonthlyAilmentReport;

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
     * @param month The target month (1-12)
     * @param year The target year (e.g., 2025)
     * @return JSON-compatible list or a message map
     */
    List<MonthlyAilmentReport> generateMonthlyAilmentsReport(int month, int year);
}
