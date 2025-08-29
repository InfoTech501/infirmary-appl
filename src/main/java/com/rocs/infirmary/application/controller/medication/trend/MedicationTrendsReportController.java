package com.rocs.infirmary.application.controller.medication.trend;

import com.rocs.infirmary.application.domain.medication.trend.MedicineAdministered;
import com.rocs.infirmary.application.service.medication.trend.MedicineAdministeredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class MedicationTrendsReportController {
    private final MedicineAdministeredService medicineAdministeredService;

    @Autowired
    public MedicationTrendsReportController(MedicineAdministeredService medicineAdministeredService) {
        this.medicineAdministeredService = medicineAdministeredService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<MedicineAdministered>> findAllMedicationTrend(){
        List<MedicineAdministered> medicineAdministeredList = this.medicineAdministeredService.findAll();
        return new ResponseEntity<>(medicineAdministeredList,HttpStatus.OK);
    }
}
