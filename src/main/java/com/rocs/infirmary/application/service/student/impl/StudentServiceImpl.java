package com.rocs.infirmary.application.service.student.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.list.StudentListResponse;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is the service implementation of {@code StudentService}
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    /**
     * creates a constructor for {@code StudentServiceImpl}
     *
     * @param studentRepository represents the student repository
     * */
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public List<StudentListResponse> findAllStudents() {
        List<Student> students = this.studentRepository.findAll();

        return students.stream().map(student -> {
            StudentListResponse AllStudents = new StudentListResponse();

            AllStudents.setLrn(student.getLrn());

            if (student.getPerson() != null &&
                    Stream.of(student.getPerson().getFirstName(), student.getPerson().getLastName(), student.getPerson().getAge())
                            .anyMatch(studVal -> studVal != null)) {

                AllStudents.setFirstName(student.getPerson().getFirstName());
                 AllStudents.setLastName(student.getPerson().getLastName());
                AllStudents.setAge(student.getPerson().getAge());
            }

            if (student.getSection() != null &&
                    Stream.of(student.getSection().getGradeLevel()).anyMatch(studVal -> studVal != null)) {

                AllStudents.setSection(student.getSection().getSection());
                AllStudents.setGradeLevel(student.getSection().getGradeLevel());
            }

            if (student.getGuardian() != null &&
                    Stream.of(student.getGuardian().getGuardianName()).anyMatch(studVal -> studVal != null)) {

                AllStudents.setGuardianName(student.getGuardian().getGuardianName());
            }

            return AllStudents;
        }).collect(Collectors.toList());
    }
}
