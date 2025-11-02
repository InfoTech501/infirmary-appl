package com.rocs.infirmary.application.service.student;



import com.rocs.infirmary.application.domain.student.list.StudentListResponse;

import java.util.List;

public interface StudentService {
    List<StudentListResponse> findAll();
}
