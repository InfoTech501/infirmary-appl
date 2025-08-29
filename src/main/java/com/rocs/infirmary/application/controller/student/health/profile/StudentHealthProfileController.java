package com.rocs.infirmary.application.controller.student.health.profile;


import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.service.student.profile.StudentHealthProfileService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentHealthProfileController {

    private StudentHealthProfileService studentHealthProfileService;
    @Autowired
    public StudentHealthProfileController(StudentHealthProfileService studentHealthProfileService) {
        this.studentHealthProfileService = studentHealthProfileService;
    }
        @PostMapping("/{id}")
        public ResponseEntity<Optional<Student>>viewStudentHealthProfileById(@PathVariable("id") long id){
        return new  ResponseEntity<>(studentHealthProfileService.viewStudentHealthProfileById(id), HttpStatus.OK);

        }
    }



