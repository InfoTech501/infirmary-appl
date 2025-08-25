package com.rocs.infirmary.application.repository.student;

import com.rocs.infirmary.application.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByLrn(Long lrn);
}
