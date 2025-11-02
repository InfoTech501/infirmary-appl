package com.rocs.infirmary.application.controller.student;


import com.rocs.infirmary.application.domain.student.clinic.visit.history.ClinicVisitHistory;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.information.StudentHealthInformation;
import com.rocs.infirmary.application.domain.student.list.StudentListResponse;
import com.rocs.infirmary.application.exception.domain.SectionNotFoundException;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.service.student.StudentService;
import com.rocs.infirmary.application.service.student.clinic.visit.history.ClinicVisitHistoryService;
import com.rocs.infirmary.application.service.student.health.information.StudentHealthInformationService;
import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
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

    private final StudentService studentListService;

    private final StudentHealthProfileService studentHealthProfileService;

    @Autowired
    public StudentController(StudentHealthInformationService studentService, ClinicVisitHistoryService clinicVisitHistoryService, StudentHealthProfileService studentHealthProfileService, StudentService studentListService) {
        this.studentService = studentService;
        this.clinicVisitHistoryService = clinicVisitHistoryService;
        this.studentHealthProfileService = studentHealthProfileService;
        this.studentListService = studentListService;

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

    /**
     * used to facilitate the request for getting the student health profile of a student based on their lrn
     *
     * @return ResponseEntity containing the Student health profile, and http Status
     * */
    @GetMapping("/health-profile")
    public ResponseEntity<StudentHealthProfileResponse> findStudentHealthProfileByLrn(@RequestParam Long lrn) {
        StudentHealthProfileResponse studentHealthProfile = studentHealthProfileService.getStudentHealthProfileByLrn(lrn);
        return new ResponseEntity<>(studentHealthProfile, HttpStatus.OK);
    }


    @GetMapping("/view-all")
    public ResponseEntity<List<StudentListResponse>> viewAllStudents(){
        return new ResponseEntity<>(this.studentListService.findAll(), HttpStatus.OK);
    }
}
