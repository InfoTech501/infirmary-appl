package com.rocs.infirmary.application.service.student.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.exception.domain.InvalidCredentialException;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.StudentService;
import com.rocs.infirmary.application.domain.student.list.StudentResponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * this is the service implementation of {@code StudentService}
 * */
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
    public List<StudentResponse> findAllStudents() {

        List<Student> students = this.studentRepository.findAll();
        List<StudentResponse> allStudentslist = new  ArrayList<>();

        for (Student student : students){
            if (student.getPerson() == null ||
                    student.getPerson().getFirstName() == null ||
                    student.getPerson().getLastName() == null ||
                    student.getPerson().getAge() <= 0) {
                throw new InvalidCredentialException("Name and Age details is missing");
            }if (student.getSection() == null || student.getSection().getGradeLevel() == null) {
                throw new InvalidCredentialException("Section details is missing");
            }if (student.getGuardian()==null) {
                throw new InvalidCredentialException("Guardian details missing");
            }

            StudentResponse allStudents = new StudentResponse();

            allStudents.setLrn(student.getLrn());
            allStudents.setFirstName(student.getPerson().getFirstName());
            allStudents.setLastName(student.getPerson().getLastName());
            allStudents.setAge(student.getPerson().getAge());
            allStudents.setSection(student.getSection().getSection());
            allStudents.setGradeLevel(student.getSection().getGradeLevel());
            allStudents.setGuardianName(student.getGuardian().getGuardianName());


            allStudentslist.add(allStudents);
        }
        return allStudentslist;
    }
}










