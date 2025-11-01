package com.rocs.infirmary.application.service.student.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.StudentService;
import com.rocs.infirmary.application.domain.student.list.StudentListView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl( StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }


    @Override
    public List<StudentListView> findAll(){
        return this.studentRepository.findAll().stream().map(this::populateViewAllStudentFields).toList();
    }
    private StudentListView populateViewAllStudentFields(Student student){
        StudentListView  allStudents = new StudentListView();

        allStudents.setLrn(student.getLrn());

        if (student.getPerson() != null) {
            allStudents.setFirstName(student.getPerson().getFirstName());
            allStudents.setLastName(student.getPerson().getLastName());
            allStudents.setAge(student.getPerson().getAge());
        }

        if (student.getSection() != null) {
            allStudents.setSection(student.getSection().getSection());
            allStudents.setGradeLevel(student.getSection().getGradeLevel());
        }

        if (student.getGuardian() != null) {
            allStudents.setGuardianName(student.getGuardian().getGuardianName());
        }

        return allStudents;
    }








}
