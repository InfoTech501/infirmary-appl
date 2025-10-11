package com.rocs.infirmary.application.controller.dashboard.medication.trend.report;

import com.rocs.infirmary.application.domain.medicine.trend.report.MedicineTrendReport;
import com.rocs.infirmary.application.service.dashboard.medication.trend.report.MedicationTrendReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
/**
 * this is used to handle the report generation of medication trends
 * */
@RestController
@RequestMapping("/dashboard")
public class MedicationTrendsReportController {
    private final MedicationTrendReportService medicineAdministeredService;
    /**
     * this creates a constructor for {@code MedicationTrendsReportController}
     * @param medicineAdministeredService is the service layer for managing report generation
     * */
    @Autowired
    public MedicationTrendsReportController(MedicationTrendReportService medicineAdministeredService) {
        this.medicineAdministeredService = medicineAdministeredService;
    }

    /**
     * this is used to facilitate request for generating medication trend report
     * @param medicineTrendReport is the object containing the request body
     *
     * @return ResponseEntity containing the medicineAdministeredList, and the Http Status
     * */
    @PostMapping("/reports/medication-trend")
    public ResponseEntity<List<MedicineTrendReport>> generateMedicationTrendReport(@RequestBody MedicineTrendReport medicineTrendReport) throws ParseException {
        List<MedicineTrendReport> medicineAdministeredList = this.medicineAdministeredService.generateMedicationTrendReport(medicineTrendReport.getStartDate(),medicineTrendReport.getEndDate());
        return new ResponseEntity<>(medicineAdministeredList,HttpStatus.OK);
    }
}
