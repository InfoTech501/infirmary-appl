package com.rocs.infirmary.application.service.student;



import com.rocs.infirmary.application.domain.student.list.StudentListView;

import java.util.List;

public interface StudentService {
    List<StudentListView> findAll();
}
