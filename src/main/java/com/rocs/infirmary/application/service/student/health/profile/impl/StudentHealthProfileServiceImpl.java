package com.rocs.infirmary.application.service.student.health.profile.impl;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileDTO;
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
    public StudentHealthProfileDTO getStudentHealthProfileById(Long id) {
        Student student = studentRepository.findStudentHealthProfileById(id);
        if (student == null) {
            LOGGER.error("student not found");
            throw new StudentNotFoundException("Student Not Found!");
        }
        return convertDto(student);
    }

    private StudentHealthProfileDTO convertDto(Student student) {
        StudentHealthProfileDTO studentHealthProfile = new StudentHealthProfileDTO();

        studentHealthProfile.setStudentId(student.getId());
        studentHealthProfile.setLrn(student.getLrn());

        if (student.getPerson() != null) {
            studentHealthProfile.setPersonId(student.getPerson().getId());
            studentHealthProfile.setFirstName(student.getPerson().getFirstName());
            studentHealthProfile.setMiddleName(student.getPerson().getMiddleName());
            studentHealthProfile.setLastName(student.getPerson().getLastName());
            studentHealthProfile.setAge(student.getPerson().getAge());
            studentHealthProfile.setBirthdate(student.getPerson().getBirthdate());
            studentHealthProfile.setGender(student.getPerson().getGender().toString());
            studentHealthProfile.setEmail(student.getPerson().getEmail());
            studentHealthProfile.setAddress(student.getPerson().getAddress());
            studentHealthProfile.setContactNumber(student.getPerson().getContactNumber());
        }

        if (student.getSection() != null) {
            studentHealthProfile.setSectionId(student.getSection().getId());
            studentHealthProfile.setStrand(student.getSection().getStrand());
            studentHealthProfile.setGradeLevel(student.getSection().getGradeLevel());
            studentHealthProfile.setSection(student.getSection().getSection());
        }
        if (student.getGuardian() != null) {
            studentHealthProfile.setGuardianId(student.getGuardian().getId());
            studentHealthProfile.setGuardianName(student.getGuardian().getGuardianName());
            studentHealthProfile.setGuardianAddress(student.getGuardian().getGuardianAddress());
            studentHealthProfile.setGuardianNumber(student.getGuardian().getGuardianNumber());
        }
        if (student.getMedicalHistory() != null) {
            studentHealthProfile.setMedicalHistoryId(student.getMedicalHistory().getMedHistoryId());
            studentHealthProfile.setDescription(student.getMedicalHistory().getDescription());
        }

        return studentHealthProfile;
    }
}








