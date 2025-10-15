package com.rocs.infirmary.application.controller.dashboard.monthly.ailment.report;

import com.rocs.infirmary.application.domain.medical.MonthlyAilmentReport.MonthlyAilmentReport;
import com.rocs.infirmary.application.service.dashboard.monthly.ailment.MonthlyAilmentReportService;
import org.springframework.http.HttpStatus;
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
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return a {@link ResponseEntity} containing the generated ailment report
     */
    @GetMapping("/reports")
    public ResponseEntity<List<MonthlyAilmentReport>> getMonthlyCommonAilments(@RequestParam String startDate, @RequestParam String endDate) {
        List<MonthlyAilmentReport> report = monthlyAilmentReportService.generateMonthlyAilmentsReport(startDate,endDate);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
