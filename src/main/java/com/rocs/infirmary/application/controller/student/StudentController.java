package com.rocs.infirmary.application.controller.student;


import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.information.StudentHealthInformation;
import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.exception.domain.SectionNotFoundException;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.service.student.health.information.StudentHealthInformationService;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is used to handle operations related to the Student services
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentHealthInformationService studentService;

    private final StudentHealthProfileService studentHealthProfileService;

    @Autowired
    public StudentController(StudentHealthInformationService studentService, StudentHealthProfileService studentHealthProfileService) {
        this.studentService = studentService;
        this.studentHealthProfileService = studentHealthProfileService;
    }


    @PutMapping("/health-profile/update")
    public ResponseEntity<Student> updateStudent(@RequestBody StudentHealthInformation student) throws StudentNotFoundException, SectionNotFoundException {
        return new ResponseEntity<>(this.studentService.updateStudentHealthInformation(student), HttpStatus.OK);

    }

    @GetMapping("/health-profile/{lrn}")
    public ResponseEntity<StudentHealthProfileResponse> findStudentHealthProfileByLrn(@PathVariable("lrn") Long lrn) {
        StudentHealthProfileResponse dto = studentHealthProfileService.getStudentHealthProfileByLrn(lrn);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
