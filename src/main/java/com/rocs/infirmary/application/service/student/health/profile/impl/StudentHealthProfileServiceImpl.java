package com.rocs.infirmary.application.service.student.health.profile.impl;

import com.rocs.infirmary.application.domain.guardian.Guardian;
import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.registration.Registration;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.exception.domain.*;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
import jakarta.transaction.Transactional;
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
    /**
     * Adds a new student's health profile into the system.
     *
     * @param student the entity containing student information and health related details to be saved
     * @return The saved Student object with all details stored in the database
     */
    @Override
    @Transactional
    public Student addStudentHealthProfile(Student student) throws StudentNotFoundException {

        if (student == null || student.getLrn() == null) {
            throw new StudentNotFoundException("LRN is required");
        }

        Student existingStudent = studentRepository.findStudentByLrn(student.getLrn());

        if (existingStudent == null) {
            throw new StudentNotFoundException("Student not yet registered");
        }

        if (existingStudent.getUser() == null) {
            LOGGER.error("Student with LRN {} does not have user account!", existingStudent.getLrn());
            throw new StudentNotFoundException("Student does not have user account");
        }

        if (existingStudent.getPerson() == null) {
            LOGGER.error("Student with LRN {} does not have personal details!", existingStudent.getLrn());
            throw new StudentNotFoundException("Student does not have personal details");
        }

        if (student.getMedicalHistory() == null) {
            throw new EmptyFieldException("Medical history is required");
        }

        if (student.getGuardian() == null) {
            throw new EmptyFieldException("Guardian information is required");
        }

        MedicalHistory newMedicalHistory = student.getMedicalHistory();
        newMedicalHistory.setStudent(existingStudent);
        existingStudent.setMedicalHistory(newMedicalHistory);

        Guardian newGuardian = student.getGuardian();
        existingStudent.setGuardian(newGuardian);

        return studentRepository.save(existingStudent);
    }
}







