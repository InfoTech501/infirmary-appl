package com.rocs.infirmary.application.controller.student;


import com.rocs.infirmary.application.domain.student.clinic.visit.history.ClinicVisitHistory;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.information.StudentHealthInformation;
import com.rocs.infirmary.application.exception.domain.SectionNotFoundException;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.service.student.clinic.visit.history.ClinicVisitHistoryService;
import com.rocs.infirmary.application.service.student.health.information.StudentHealthInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller is used to handle operations related to the Student services
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentHealthInformationService studentService;
    private final ClinicVisitHistoryService clinicVisitHistoryService;
    @Autowired
    public StudentController(StudentHealthInformationService studentService, ClinicVisitHistoryService clinicVisitHistoryService) {
        this.studentService = studentService;
        this.clinicVisitHistoryService = clinicVisitHistoryService;
    }

    @PutMapping("/health-profile/update")
    public ResponseEntity<Student> updateStudent(@RequestBody StudentHealthInformation student) throws StudentNotFoundException, SectionNotFoundException {
        return new ResponseEntity<>(this.studentService.updateStudentHealthInformation(student),HttpStatus.OK);
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
