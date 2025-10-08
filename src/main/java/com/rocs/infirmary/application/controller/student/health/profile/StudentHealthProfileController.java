package com.rocs.infirmary.application.controller.student.health.profile;

import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileDTO;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
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

    private final StudentHealthProfileService studentHealthProfileService;

    @Autowired
    public StudentHealthProfileController(StudentHealthProfileService studentHealthProfileService) {
        this.studentHealthProfileService = studentHealthProfileService;
    }

    /**
     * Retrieves a student's health profile by their ID.
     * @param id is the unique identifier of a student
     * @return the student's health profile DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentHealthProfileDTO> findStudentHealthProfileById(@PathVariable("id") long id) {
        StudentHealthProfileDTO dto = studentHealthProfileService.getStudentHealthProfileById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
