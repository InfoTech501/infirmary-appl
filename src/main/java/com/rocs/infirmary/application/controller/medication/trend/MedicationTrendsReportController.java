package com.rocs.infirmary.application.controller.medication.trend;

import com.rocs.infirmary.application.domain.medicine.trend.report.MedicineTrendReport;
import com.rocs.infirmary.application.service.medication.trend.MedicationTrendReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class MedicationTrendsReportController {
    private final MedicationTrendReportService medicineAdministeredService;

    @Autowired
    public MedicationTrendsReportController(MedicationTrendReportService medicineAdministeredService) {
        this.medicineAdministeredService = medicineAdministeredService;
    }

    @PostMapping("/reports/medication-trend")
    public ResponseEntity<List<MedicineTrendReport>> generateMedicationTrendReport(@RequestBody MedicineTrendReport medicineTrendReport) throws ParseException {
        List<MedicineTrendReport> medicineAdministeredList = this.medicineAdministeredService.generateMedicationTrendReport(medicineTrendReport.getStartDate(),medicineTrendReport.getEndDate());
        return new ResponseEntity<>(medicineAdministeredList,HttpStatus.OK);
    }
}
