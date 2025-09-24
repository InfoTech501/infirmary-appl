package com.rocs.infirmary.application.controller.student.health.profile;


import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.service.student.profile.StudentHealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * StudentHealthProfileController handles requests for retrieving
 * student health profile with details such as medical history,
 * names, section and other health-related information of the student.
 */
@RestController
@RequestMapping("/student")
public class StudentHealthProfileController {

    private StudentHealthProfileService studentHealthProfileService;

    /**
     * Constructor for StudentHealthProfileController.
     * @param studentHealthProfileService the service used to handle student health profile operations
     */
    @Autowired
    public StudentHealthProfileController(StudentHealthProfileService studentHealthProfileService) {
        this.studentHealthProfileService = studentHealthProfileService;
    }

    /**
     * retrieves a student's health profile by their ID
     * @param id is the unique identifier of a student
     * returns the student's health profile with all the details of the student
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student>findStudentHealthProfileById(@PathVariable("id") long id){
        return new  ResponseEntity<>(studentHealthProfileService.findStudentHealthProfileById(id), HttpStatus.OK);
    }
}


