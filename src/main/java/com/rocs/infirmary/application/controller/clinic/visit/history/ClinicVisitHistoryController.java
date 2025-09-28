package com.rocs.infirmary.application.controller.clinic.visit.history;

import com.rocs.infirmary.application.domain.clinic.visit.history.ClinicVisitHistory;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.service.clinic.visit.history.ClinicVisitHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * this is used to handle the Student's  Clinic Visit History
 * */
@RestController
@RequestMapping("/student")
public class ClinicVisitHistoryController {
    private final ClinicVisitHistoryService clinicVisitHistoryService;
    /**
     * this creates a constructor for {@code ClinicVisitHistoryController}
     * @param clinicVisitHistoryService is the service layer for managing the logic for student clinic visit history
     * */
    @Autowired
    public ClinicVisitHistoryController(ClinicVisitHistoryService clinicVisitHistoryService) {
        this.clinicVisitHistoryService = clinicVisitHistoryService;
    }
    /**
     * this is used to facilitate request for getting the Student Clinic Visit History
     * @param lrn is used as the unique identifier for each student
     *
     * @return ResponseEntity containing the Student clinic visit history, and the Http Status
     * */
    @GetMapping("/clinic/visit")
    public ResponseEntity<List<ClinicVisitHistory>> viewClinicVisitHistory(@RequestParam Long lrn) throws StudentNotFoundException {
        return new ResponseEntity<>(this.clinicVisitHistoryService.findClinicVisitByStudentLrn(lrn), HttpStatus.OK);
    }
    /**
     * this is used to facilitate request for getting all Clinic Visit History for all student
     *
     * @return ResponseEntity containing the Student clinic visit history, and the Http Status
     * */
    @GetMapping("/clinic/visit/view-all")
    public ResponseEntity<List<ClinicVisitHistory>> viewClinicVisitHistory(){
        return new ResponseEntity<>(this.clinicVisitHistoryService.findAllClinicVisits(), HttpStatus.OK);
    }
}
