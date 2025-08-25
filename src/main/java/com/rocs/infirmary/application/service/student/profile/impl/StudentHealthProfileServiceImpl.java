package com.rocs.infirmary.application.service.student.profile.impl;

import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.dto.student.heath.profile.CreateStudentHealthProfileRequestDTO;
import com.rocs.infirmary.application.repository.medical.history.MedicalHistoryRepository;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.profile.StudentHealthProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentHealthProfileServiceImpl implements StudentHealthProfileService {

   private StudentRepository studentRepository;
   private MedicalHistoryRepository medicalHistoryRepository;
    @Autowired
    public StudentHealthProfileServiceImpl(StudentRepository studentRepository, MedicalHistoryRepository medicalHistoryRepository) {
        this.studentRepository = studentRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
    }
    @Override
    @Transactional
    public Student createStudentHealthProfile(CreateStudentHealthProfileRequestDTO dto) {

            validateRequiredFields(dto);

            if(studentRepository.existsByLrn(dto.getLrn())){
                throw new IllegalArgumentException("Student with LRN " + dto.getLrn() + " already exists");
            }

            Person person = new Person();
            person.setFirstName(dto.getFirstName());
            person.setMiddleName(dto.getMiddleName());
            person.setLastName(dto.getLastName());
            person.setBirthdate(dto.getBirthdate());
            person.setContactNumber(dto.getContactNumber());
            person.setEmail(dto.getEmail());
            person.setAddress(dto.getAddress());

            Section section = new Section();
            section.setGradeLevel(dto.getGradeLevel());
            section.setSection(dto.getSection());

            MedicalHistory medicalHistory = new MedicalHistory();
            medicalHistory.setDescription(dto.getDescription());
            medicalHistoryRepository.save(medicalHistory);

            Student student = new Student();
            student.setLrn(dto.getLrn());
            student.setPerson(person);
            student.setSection(section);

           return studentRepository.save(student);
    }

    private void validateRequiredFields(CreateStudentHealthProfileRequestDTO dto) {
        if (dto.getLrn() == null) {
            throw new IllegalArgumentException("LRN is required and cannot be empty");
        }

        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required and cannot be empty");
        }

        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required and cannot be empty");
        }

        if (dto.getBirthdate() == null) {
            throw new IllegalArgumentException("Birthdate is required and cannot be empty");
        }

        if (dto.getContactNumber() == null || dto.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number is required and cannot be empty");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required and cannot be empty");
        }

        if (dto.getGradeLevel() == null || dto.getGradeLevel().trim().isEmpty()) {
            throw new IllegalArgumentException("Grade level is required and cannot be empty");
        }

        if (dto.getSection() == null || dto.getSection().trim().isEmpty()) {
            throw new IllegalArgumentException("Section is required and cannot be empty");
        }

        if (dto.getAddress() == null || dto.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required and cannot be empty");
        }
    }
}

