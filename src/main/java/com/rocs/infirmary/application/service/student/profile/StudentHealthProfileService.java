package com.rocs.infirmary.application.service.student.profile;

import com.rocs.infirmary.application.domain.student.Student;
import org.springframework.stereotype.Service;


import java.util.Optional;
@Service
public interface StudentHealthProfileService {

  Optional<Student> viewStudentHealthProfileById(Long id);
}
