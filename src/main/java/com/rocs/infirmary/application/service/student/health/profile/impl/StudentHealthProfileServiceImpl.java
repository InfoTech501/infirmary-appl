package com.rocs.infirmary.application.service.student.health.profile.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
import com.rocs.infirmary.application.exception.domain.StudentHealthProfileNotFoundException;
import com.rocs.infirmary.application.exception.domain.InvalidCredentialException;
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
    public StudentHealthProfileResponse getStudentHealthProfileByLrn(Long lrn) throws StudentHealthProfileNotFoundException, InvalidCredentialException {

        checkLrn(lrn);
        Student student = studentRepository.findStudentByLrn(lrn);

        StudentHealthProfileResponse studentHealthProfile = new StudentHealthProfileResponse();

        studentHealthProfile.setStudent(new Student());
        studentHealthProfile.getStudent().setId(student.getId());
        studentHealthProfile.getStudent().setLrn(student.getLrn());
        studentHealthProfile.getStudent().setPerson(student.getPerson());
        studentHealthProfile.getStudent().setSection(student.getSection());
        studentHealthProfile.getStudent().setGuardian(student.getGuardian());
        studentHealthProfile.getStudent().setMedicalHistory(student.getMedicalHistory());

        return studentHealthProfile;
    }

    private void checkLrn(Long lrn) {

        if (String.valueOf(lrn).length() != 12) {
            throw new InvalidCredentialException("LRN must be 12 digits");
        }
        Student studentlrn = studentRepository.findStudentByLrn(lrn);
        if(studentlrn == null){
            throw new StudentHealthProfileNotFoundException("lrn do not exist");
        }
    }
}









