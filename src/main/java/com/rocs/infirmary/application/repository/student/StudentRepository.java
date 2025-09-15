package com.rocs.infirmary.application.repository.student;

import com.rocs.infirmary.application.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByPersonEmail(String email);
}
