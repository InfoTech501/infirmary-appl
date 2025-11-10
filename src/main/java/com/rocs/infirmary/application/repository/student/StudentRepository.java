package com.rocs.infirmary.application.repository.student;

import com.rocs.infirmary.application.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * {@code StudentRepository} is an interface for student repository
 * */
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByPersonEmail(String email);
    Student findStudentByUserId(Long id);
    Student findStudentByLrn(Long lrn);
}
