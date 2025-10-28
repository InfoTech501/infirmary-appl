package com.rocs.infirmary.application.service.student.health.profile.impl;

import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.exception.domain.EmptyFieldException;
import com.rocs.infirmary.application.exception.domain.StudentAlreadyExistException;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
import com.rocs.infirmary.application.service.student.health.profile.exception.StudentHealthProfileNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.rocs.infirmary.application.exception.constants.student.StudentExceptionConstants.*;

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

    /**
     * Adds a new student's health profile into the system.
     *
     * @param student the entity containing student information and health related details to be saved
     * @return The saved Student object with all details stored in the database
     */
    @Override
    @Transactional
    public Student addStudentHealthProfile(Student student) {

        validateRequiredFields(student);

        Student existByLrn = studentRepository.findStudentByLrn(student.getLrn());

        if (existByLrn != null) {
            LOGGER.error("Student with LRN {} already exists!", student.getLrn());
            throw new StudentAlreadyExistException(STUDENT_ALREADY_EXISTS);
        }
        Student newStudent = new Student();
        newStudent.setLrn(student.getLrn());

        Person person = student.getPerson();
        newStudent.setPerson(person);

        Section section = student.getSection();
        newStudent.setSection(section);

        MedicalHistory medicalHistory = student.getMedicalHistory();
        newStudent.setMedicalHistory(medicalHistory);
        return studentRepository.save(newStudent);
    }

    private void validateRequiredFields(Student student) {

        if (student.getLrn() == null) {
            LOGGER.error("Validation failed: LRN is missing");
            throw new EmptyFieldException(LRN_REQUIRED);
        }
        if (student.getPerson().getFirstName() == null || student.getPerson().getFirstName().trim().isEmpty()) {
            LOGGER.error("Validation failed: First name is missing");
            throw new EmptyFieldException(FIRST_NAME_REQUIRED);
        }
        if (student.getPerson().getLastName() == null || student.getPerson().getLastName().trim().isEmpty()) {
            LOGGER.error("Validation failed: Last name is missing");
            throw new EmptyFieldException(LAST_NAME_REQUIRED);
        }
        if (student.getPerson().getBirthdate() == null) {
            LOGGER.error("Validation failed: Birthdate is missing");
            throw new EmptyFieldException(BIRTHDAY_REQUIRED);
        }
        if (student.getPerson().getGender() == null || student.getPerson().getGender().trim().isEmpty()) {
            LOGGER.error("Validation failed: Gender is missing");
            throw new EmptyFieldException(GENDER_REQUIRED);
        }
        if (student.getPerson().getEmail() == null || student.getPerson().getEmail().trim().isEmpty()) {
            LOGGER.error("Validation failed: Email is missing");
            throw new EmptyFieldException(EMAIL_REQUIRED);
        }
        if (student.getPerson().getAddress() == null || student.getPerson().getAddress().trim().isEmpty()) {
            LOGGER.error("Validation failed: Address is missing");
            throw new EmptyFieldException(ADDRESS_REQUIRED);
        }
        if (student.getPerson().getContactNumber() == null || student.getPerson().getContactNumber().trim().isEmpty()) {
            LOGGER.error("Validation failed: Contact number is missing");
            throw new EmptyFieldException(CONTACT_NUMBER_REQUIRED);
        }
        if (student.getSection().getGradeLevel() == null || student.getSection().getGradeLevel().trim().isEmpty()) {
            LOGGER.error("Validation failed: Grade level is missing");
            throw new EmptyFieldException(GRADE_LEVEL_REQUIRED);
        }
        if (student.getSection().getSection() == null || student.getSection().getSection().trim().isEmpty()) {
            LOGGER.error("Validation failed: Section is missing");
            throw new EmptyFieldException(SECTION_REQUIRED);
        }
    }
}









