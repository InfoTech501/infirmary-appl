package com.rocs.infirmary.application.service.student.health.profile.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
import com.rocs.infirmary.application.service.student.health.profile.exception.StudentHealthProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Implementation of the StudentHealthProfileService interface.
 * */
@Service
public class StudentHealthProfileServiceImpl implements StudentHealthProfileService {

    private final StudentRepository studentRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileServiceImpl.class);

    /**
     * this creates a constructor for {@code StudentHealthProfileServiceImpl}
     *
     * @param studentRepository represents the student repository
     */

    @Autowired
    public StudentHealthProfileServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentHealthProfileResponse getStudentHealthProfileByLrn(Long lrn) {


        Student student = studentRepository.findStudentByLrn(lrn);

        if (student==null) {
            LOGGER.error("Health profile not found");
            throw new StudentHealthProfileNotFoundException("Student Health profile not found");
        }

        StudentHealthProfileResponse studentHealthProfile = new StudentHealthProfileResponse();

        studentHealthProfile.setStudent(student);

        return studentHealthProfile;
    }
}









