package com.rocs.infirmary.application.service.student.health.profile.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.exception.domain.InvalidLRNexception;
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
    public StudentHealthProfileResponse getStudentHealthProfileByLrn(Long lrn) throws StudentNotFoundException, InvalidLRNexception {

        checkLrn(lrn);
        Student student = studentRepository.findStudentByLrn(lrn);

        if (student==null) {
            LOGGER.error("Health profile not found");
            throw new StudentNotFoundException("Student Health profile not found");
        }


        StudentHealthProfileResponse studentHealthProfile = new StudentHealthProfileResponse();

        studentHealthProfile.setStudent(student);

        return studentHealthProfile;
    }

    private void checkLrn(Long lrn) {
        if (lrn == null) {
            throw new InvalidLRNexception("LRN must not be null");
        }


//        if (lrn <= 0) {
//            throw new InvalidLRNexception("LRN must be a positive number");
//        }

        int length = String.valueOf(lrn).length();
        if (length != 12) {
            throw new InvalidLRNexception("LRN must be 12 digits");
        }
    }
}









