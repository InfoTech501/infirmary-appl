package com.rocs.infirmary.application.controller.parent;

import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.exception.domain.InvalidTokenException;
import com.rocs.infirmary.application.service.student.health.profile.impl.StudentHealthProfileServiceImpl;
import com.rocs.infirmary.application.service.user.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/parent")
public class ParentController {
    private final StudentHealthProfileServiceImpl studentHealthProfileService;
    private final UserServiceImpl userService;

    @Autowired
    public ParentController(StudentHealthProfileServiceImpl studentHealthProfileService, UserServiceImpl userService) {
        this.studentHealthProfileService = studentHealthProfileService;
        this.userService = userService;
    }

    @GetMapping("/view/student-profile")
    public ResponseEntity<StudentHealthProfileResponse> getChildProfile(@RequestParam String token){
        Long lrn = Long.valueOf(this.userService.getSubjectFromParentToken(token));
        if(lrn == null){
            throw new InvalidTokenException("Invalid token subject");
        }
        StudentHealthProfileResponse studentHealthProfileResponse = this.studentHealthProfileService.getStudentHealthProfileByLrn(lrn);
        return new ResponseEntity<>(studentHealthProfileResponse,HttpStatus.OK);
    }
}
