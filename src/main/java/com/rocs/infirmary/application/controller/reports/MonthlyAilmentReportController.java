package com.rocs.infirmary.application.controller.reports;

import com.rocs.infirmary.application.domain.medicalRecord.MonthlyReport.MonthlyReport;
import com.rocs.infirmary.application.service.report.monthlyAilment.MonthlyAilmentReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for handling monthly ailment report requests.
 * <p>
 * This class exposes a REST endpoint to generate a report of the common
 * ailments for a specified MONTH and YEAR. It uses the {@link MonthlyAilmentReportService}
 * to perform the business logic and returns the report data.
 * </p>
 */
@RestController
@RequestMapping("/monthly-ailment")
@Slf4j
public class MonthlyAilmentReportController {
  private final MonthlyAilmentReportService monthlyAilmentReportService;

  /**
   * Constructs a new MonthlyAilmentReportController with the required service dependency.
   *
   * @param monthlyAilmentReportService The service that contains the business logic for generating the report.
   */
  public MonthlyAilmentReportController(MonthlyAilmentReportService monthlyAilmentReportService) {
    this.monthlyAilmentReportService = monthlyAilmentReportService;
  }

    /**
     * Handles the generation of monthly common ailment reports.
     *
     * @param month the month for which the report is to be generated (1–12)
     * @param year the year for which the report is to be generated (e.g. 2023, 2024, 2025)
     * @return a {@link ResponseEntity} containing the generated ailment report
     */
    @GetMapping("/reports")
    public ResponseEntity<?> getMonthlyCommonAilments(@RequestParam int month, @RequestParam int year) {
        List<MonthlyReport> report = monthlyAilmentReportService.generateMonthlyAilmentsReport(month, year);
        return ResponseEntity.ok(report);
    }
}
