package com.rocs.infirmary.application.controller.frequent.visit;

import com.rocs.infirmary.application.domain.medicalRecord.FrequentVisitReport.FrequentVisitReport;
import com.rocs.infirmary.application.service.frequent.visit.FrequentVisitReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling frequent visit report requests.
 * <p>
 * This class exposes a REST endpoint to generate a report of the most
 * frequently visited cases within a specified date range. It uses the {@link FrequentVisitReportService}
 * to perform the business logic and returns the report data.
 * </p>
 */
@RestController
@RequestMapping("/frequent-visit")
@Slf4j
public class FrequentVisitReportController {
    private final FrequentVisitReportService frequentVisitReportService;

    /**
     * Constructs a new FrequentVisitReportController with the required service dependency.
     *
     * @param frequentVisitReportService The service that contains the business logic for generating the report.
     */
    public FrequentVisitReportController(FrequentVisitReportService frequentVisitReportService) {
        this.frequentVisitReportService = frequentVisitReportService;
    }

    /**
     * Handles the generation of frequent visit reports.
     *
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return a {@link ResponseEntity} containing the generated frequent visit report
     */
    @GetMapping("/reports")
    public ResponseEntity<List<FrequentVisitReport>> getFrequentVisitReport(@RequestParam String startDate, @RequestParam String endDate) {
        List<FrequentVisitReport> report = frequentVisitReportService.generateFrequentVisitReport(startDate, endDate);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}