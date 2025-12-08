package com.rocs.infirmary.application.repository.student;

import com.rocs.infirmary.application.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code StudentRepository} is an interface for student repository
 * */
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByPersonEmail(String email);
    Student findStudentByUserId(Long id);
    Student findStudentByLrn(Long lrn);
    List<Student> findAll();

}
