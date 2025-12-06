package com.rocs.infirmary.application.service.student;

import com.rocs.infirmary.application.domain.student.list.StudentResponse;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;

import java.util.List;

/**
 * {@code StudentService} is the interface of the StudentService
 * */

public interface StudentService {

    /**
     * this finds all students
     * */
    List<StudentResponse> findAllStudents() throws StudentNotFoundException;
}
