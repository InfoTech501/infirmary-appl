package com.rocs.infirmary.application.service.student;

import com.rocs.infirmary.application.domain.student.list.StudentResponse;

import java.util.List;

/**
 * {@code StudentService} is the interface of the StudentService
 * */

public interface StudentService {
    List<StudentResponse> findAllStudents();
}
