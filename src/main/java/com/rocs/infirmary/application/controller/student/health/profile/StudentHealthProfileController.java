package com.rocs.infirmary.application.controller.student.health.profile;

import com.rocs.infirmary.application.domain.student.dto.student.heath.profile.CreateStudentHealthProfileRequestDTO;
import com.rocs.infirmary.application.service.student.profile.StudentHealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentHealthProfileController  {
    private final StudentHealthProfileService service;

    @Autowired
    public StudentHealthProfileController(StudentHealthProfileService service) {
        this.service = service;
    }

    @PostMapping("/submit-health-profile")
    public ResponseEntity<String> createStudentHealthProfile(@RequestBody CreateStudentHealthProfileRequestDTO dto) {
        service.createStudentHealthProfile(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student Health Profile Created Successfully");

    }

}
