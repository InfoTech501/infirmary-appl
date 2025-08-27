package com.rocs.infirmary.application.repository.student;

import com.rocs.infirmary.application.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * {@code StudentRepository} is an interface of Student repository
 * */
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    /**
     * Checks if a student with the given LRN already exists in the database.
     *
     * @param lrn the Learner Reference Number (LRN) of the student
     * @return {@code true} if a student with the given LRN exists,
     * otherwise {@code false}
     */
    boolean existsByLrn(Long lrn);
}
