package com.rocs.infirmary.application.service.student.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.exception.domain.InvalidCredentialException;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.StudentService;
import com.rocs.infirmary.application.domain.student.list.StudentResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * this is the service implementation of {@code StudentService}
 * */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }


    @Override
    public List<StudentResponse> findAllStudents() {

        List<Student> students = this.studentRepository.findAll();

        return (students.stream().map(student -> {
            StudentResponse allStudents = new StudentResponse();

            allStudents.setLrn(student.getLrn());

            if (student.getPerson() == null ||
                    student.getPerson().getFirstName() == null ||
                    student.getPerson().getLastName() == null ||
                    student.getPerson().getAge() <= 0) {

                throw new InvalidCredentialException("Name and Age details is missing");
            }

                allStudents.setFirstName(student.getPerson().getFirstName());
                allStudents.setLastName(student.getPerson().getLastName());
                allStudents.setAge(student.getPerson().getAge());

            if (student.getSection() == null ||
                    student.getSection().getGradeLevel() == null) {
                throw new InvalidCredentialException("Section details is missing");
            }
                allStudents.setSection(student.getSection().getSection());
                allStudents.setGradeLevel(student.getSection().getGradeLevel());

            if (student.getGuardian() == null ||
                    student.getGuardian().getGuardianName() == null) {
              throw new InvalidCredentialException("Guardian details missing");
            }
                allStudents.setGuardianName(student.getGuardian().getGuardianName());

            return allStudents;
        }).collect(Collectors.toList()));
    }
}










