package com.rocs.infirmary.application.service.frequent.visit;

import com.rocs.infirmary.application.domain.medicalRecord.FrequentVisitReport.FrequentVisitReport;

import java.util.List;

/**
 * Service interface for generating frequent visit reports.
 * <p>
 * This interface defines the contract for generating reports that summarize
 * the number of visits per student within a specified date range.
 * </p>
 */
public interface FrequentVisitReportService {
    /**
     * Generates a frequent visit report with student details and visit counts.
     *
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return list of frequent visit report entries
     */
    List<FrequentVisitReport> generateFrequentVisitReport(String startDate, String endDate);
}