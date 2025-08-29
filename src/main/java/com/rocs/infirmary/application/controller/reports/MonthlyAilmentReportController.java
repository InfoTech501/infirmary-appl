package com.rocs.infirmary.application.controller.reports;

import com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.request.MonthlyAilmentsReportRequestDto;
import com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.response.MonthlyAilmentsReportResponseDto;
import com.rocs.infirmary.application.exception.domain.MonthlyAilmentReportException;
import com.rocs.infirmary.application.service.report.monthlyAilment.MonthlyAilmentReportService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for handling monthly ailment report requests.
 * <p>
 * This class exposes a REST endpoint to generate a report of the common
 * ailments for a specified MONTH and YEAR. It uses the {@link MonthlyAilmentReportService}
 * to perform the business logic and returns the report data.
 * </p>
 */
@RestController
@RequestMapping("/monthly-ailment-report")
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
   * Endpoint to generate a monthly ailments report.
   * This method processes a POST request with the specified YEAR, MONTH, and LIMIT(OPTIONAL) to create a report of the most common ailments on that period.
   *
   * @param request The {@link MonthlyAilmentsReportRequestDto} containing the YEAR, MONTH, and LIMIT. The request body is validated.
   * @return A {@link ResponseEntity} containing a {@link MonthlyAilmentsReportResponseDto} with the report data.
   */
  @PostMapping("/monthly-ailments")
  public ResponseEntity<MonthlyAilmentsReportResponseDto> generateMonthlyAilmentsReport(@Valid @RequestBody MonthlyAilmentsReportRequestDto request) {
      log.info("Received request for monthly ailments report: {}/{}", request.getMonth(), request.getYear());
      try {
          MonthlyAilmentsReportResponseDto report = monthlyAilmentReportService.generateMonthlyAilmentsReport(request);
          if (report.getTotalRecords() == 0) {
              log.info("No data available for the requested period");
          } else {
              log.info("Successfully generated monthly ailments report with {} records", report.getTotalRecords());
          }
          return ResponseEntity.ok(report);
      } catch (MonthlyAilmentReportException e) {
          log.error("Failed to generate report due to a business logic error: {}", e.getMessage());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
  }
}
