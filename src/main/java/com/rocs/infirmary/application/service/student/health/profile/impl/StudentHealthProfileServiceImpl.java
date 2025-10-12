package com.rocs.infirmary.application.service.student.health.profile.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
import com.rocs.infirmary.application.service.student.health.profile.exceptions.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentHealthProfileServiceImpl implements StudentHealthProfileService {

    private final StudentRepository studentRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileServiceImpl.class);


    @Autowired
    public StudentHealthProfileServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentHealthProfileResponse getStudentHealthProfileByLrn(Long lrn) {
        Student student = studentRepository.findStudentHealthProfileByLrn(lrn);
        if (student == null) {
            LOGGER.error("student not found");
            throw new StudentNotFoundException("Student Not Found!");
        }
        return convertDto(student);
    }

    private StudentHealthProfileResponse convertDto(Student student) {
        StudentHealthProfileResponse studentHealthProfile = new StudentHealthProfileResponse();

        studentHealthProfile.setStudent(student);
        studentHealthProfile.setPerson(student.getPerson());
        studentHealthProfile.setSection(student.getSection());
        studentHealthProfile.setGuardian(student.getGuardian());
        studentHealthProfile.setMedicalHistory(student.getMedicalHistory());


        return studentHealthProfile;
    }
}








