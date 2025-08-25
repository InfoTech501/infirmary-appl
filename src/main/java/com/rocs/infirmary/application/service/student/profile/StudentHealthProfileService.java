package com.rocs.infirmary.application.service.student.profile;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.dto.student.heath.profile.CreateStudentHealthProfileRequestDTO;

public interface StudentHealthProfileService {

     Student createStudentHealthProfile(CreateStudentHealthProfileRequestDTO dto);
}
