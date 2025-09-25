package com.rocs.infirmary.application.service.student.profile.impl;

import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.person.student.Student;
import com.rocs.infirmary.application.exception.domain.EmptyFieldException;
import com.rocs.infirmary.application.exception.domain.StudentAlreadyExistException;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.profile.StudentHealthProfileService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rocs.infirmary.application.exception.constants.student.StudentExceptionConstants.*;
/**
 * Service class that handles the student health profile.
 */
@Service
public class StudentHealthProfileServiceImpl implements StudentHealthProfileService {
    private static Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileServiceImpl.class);

    private StudentRepository studentRepository;
    /**
     * Constructor for {@code StudentHealthProfileServiceImpl}.
     *
     * @param studentRepository the repository used to save student records
     */
    @Autowired
    public StudentHealthProfileServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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

        if (studentRepository.existsByLrn(student.getLrn())) {
            LOGGER.error("Student with LRN {} already exists!", student.getLrn());
            throw new StudentAlreadyExistException(STUDENT_ALREADY_EXISTS);
        }
        Student newStudent = new Student();
        newStudent.setLrn(student.getLrn());
        newStudent.setFirstName(student.getFirstName());
        newStudent.setMiddleName(student.getMiddleName());
        newStudent.setLastName(student.getLastName());
        newStudent.setAge(student.getAge());
        newStudent.setBirthdate(student.getBirthdate());
        newStudent.setGender(student.getGender());
        newStudent.setEmail(student.getEmail());
        newStudent.setAddress(student.getAddress());
        newStudent.setContactNumber(student.getContactNumber());

        Section section = student.getSection();
        newStudent.setSection(section);

        MedicalHistory medicalHistory = student.getMedicalHistory();
        medicalHistory.setDescription(student.getMedicalHistory().getDescription());
        newStudent.setMedicalHistory(medicalHistory);

        return studentRepository.save(newStudent);
    }

    private void validateRequiredFields(Student student) {

        if (student.getLrn() == null) {
            LOGGER.error("Validation failed: LRN is missing");
            throw new EmptyFieldException(LRN_REQUIRED);
        }
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            LOGGER.error("Validation failed: First name is missing");
            throw new EmptyFieldException(FIRST_NAME_REQUIRED);
        }
        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            LOGGER.error("Validation failed: Last name is missing");
            throw new EmptyFieldException(LAST_NAME_REQUIRED);
        }
        if (student.getBirthdate() == null) {
            LOGGER.error("Validation failed: Birthdate is missing");
            throw new EmptyFieldException(BIRTHDAY_REQUIRED);
        }
        if (student.getGender() == null || student.getGender().trim().isEmpty()) {
            LOGGER.error("Validation failed: Gender is missing");
            throw new EmptyFieldException(GENDER_REQUIRED);
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            LOGGER.error("Validation failed: Email is missing");
            throw new EmptyFieldException(EMAIL_REQUIRED);
        }
        if (student.getAddress() == null || student.getAddress().trim().isEmpty()) {
            LOGGER.error("Validation failed: Address is missing");
            throw new EmptyFieldException(ADDRESS_REQUIRED);
        }
        if (student.getContactNumber() == null || student.getContactNumber().trim().isEmpty()) {
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

