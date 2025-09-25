package com.rocs.infirmary.application.service.student.profile;

import com.rocs.infirmary.application.domain.student.Student;
import org.springframework.stereotype.Service;


import java.util.Optional;

public interface StudentHealthProfileService {

    /**
     * finds a student by id
     * @param id is the unique identifier of the student
     * @return User
     * */
    Student findStudentHealthProfileById(Long id);


}