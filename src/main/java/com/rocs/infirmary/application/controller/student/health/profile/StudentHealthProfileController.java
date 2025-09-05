package com.rocs.infirmary.application.controller.student.health.profile;

import com.rocs.infirmary.application.domain.person.student.Student;
import com.rocs.infirmary.application.service.student.profile.StudentHealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * {@code StudentHealthProfileController} handles requests related
 * to creating and saving student health profiles.
 */
@RestController
@RequestMapping("/student")
public class StudentHealthProfileController  {
    private final StudentHealthProfileService service;
    /**
     * Constructor for {@code StudentHealthProfileController}.
     *
     * @param service the service used to handle student health profile logic
     */
    @Autowired
    public StudentHealthProfileController(StudentHealthProfileService service) {
        this.service = service;
    }
    /**
     * Submits a new student health profile.
     *
     * @param student the student object with health profile details
     * @return the saved student with status {@code OK}
     */
    @PostMapping("/submit-health-profile")
    public ResponseEntity<Student> createStudentHealthProfile(@RequestBody Student student) {
        service.addStudentHealthProfile(student);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }
}
