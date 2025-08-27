package com.rocs.infirmary.application.service.student.profile.impl;

import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.repository.medical.history.MedicalHistoryRepository;
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
    private MedicalHistoryRepository medicalHistoryRepository;
    /**
     * Constructor for {@code StudentHealthProfileServiceImpl}.
     *
     * @param studentRepository the repository used to save student records
     * @param medicalHistoryRepository the repository used to save medical history records
     */
    @Autowired
    public StudentHealthProfileServiceImpl(StudentRepository studentRepository, MedicalHistoryRepository medicalHistoryRepository) {
        this.studentRepository = studentRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
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
            throw new IllegalArgumentException(STUDENT_ALREADY_EXISTS);
        }
        Person person = new Person();
        person.setFirstName(student.getPerson().getFirstName());
        person.setMiddleName(student.getPerson().getMiddleName());
        person.setLastName(student.getPerson().getLastName());
        person.setAge(student.getPerson().getAge());
        person.setBirthdate(student.getPerson().getBirthdate());
        person.setGender(student.getPerson().getGender());
        person.setEmail(student.getPerson().getEmail());
        person.setAddress(student.getPerson().getAddress());
        person.setContactNumber(student.getPerson().getContactNumber());

        Section section = new Section();
        section.setGradeLevel(student.getSection().getGradeLevel());
        section.setSection(student.getSection().getSection());

        student.setLrn(student.getLrn());
        student.setPerson(person);
        student.setSection(section);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setDescription(medicalHistory.getDescription());
        medicalHistoryRepository.save(medicalHistory);

        return studentRepository.save(student);
    }

    private void validateRequiredFields(Student student) {

        if (student.getLrn() == null) {
            LOGGER.error("Validation failed: LRN is missing");
            throw new IllegalArgumentException(LRN_REQUIRED);
        }
        if (student.getPerson().getFirstName() == null || student.getPerson().getFirstName().trim().isEmpty()) {
            LOGGER.error("Validation failed: First name is missing");
            throw new IllegalArgumentException(FIRST_NAME_REQUIRED);
        }
        if (student.getPerson().getLastName() == null || student.getPerson().getLastName().trim().isEmpty()) {
            LOGGER.error("Validation failed: Last name is missing");
            throw new IllegalArgumentException(LAST_NAME_REQUIRED);
        }
        if (student.getPerson().getBirthdate() == null) {
            LOGGER.error("Validation failed: Birthdate is missing");
            throw new IllegalArgumentException(BIRTHDAY_REQUIRED);
        }
        if (student.getPerson().getGender() == null || student.getPerson().getGender().trim().isEmpty()) {
            LOGGER.error("Validation failed: Gender is missing");
            throw new IllegalArgumentException(GENDER_REQUIRED);
        }
        if (student.getPerson().getEmail() == null || student.getPerson().getEmail().trim().isEmpty()) {
            LOGGER.error("Validation failed: Email is missing");
            throw new IllegalArgumentException(EMAIL_REQUIRED);
        }
        if (student.getPerson().getAddress() == null || student.getPerson().getAddress().trim().isEmpty()) {
            LOGGER.error("Validation failed: Address is missing");
            throw new IllegalArgumentException(ADDRESS_REQUIRED);
        }
        if (student.getPerson().getContactNumber() == null || student.getPerson().getContactNumber().trim().isEmpty()) {
            LOGGER.error("Validation failed: Contact number is missing");
            throw new IllegalArgumentException(CONTACT_NUMBER_REQUIRED);
        }
        if (student.getSection().getGradeLevel() == null || student.getSection().getGradeLevel().trim().isEmpty()) {
            LOGGER.error("Validation failed: Grade level is missing");
            throw new IllegalArgumentException(GRADE_LEVEL_REQUIRED);
        }
        if (student.getSection().getSection() == null || student.getSection().getSection().trim().isEmpty()) {
            LOGGER.error("Validation failed: Section is missing");
            throw new IllegalArgumentException(SECTION_REQUIRED);
        }
    }
}

