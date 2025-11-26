package com.rocs.infirmary.application.service.student.health.information.impl;

import com.rocs.infirmary.application.domain.guardian.Guardian;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.information.StudentHealthInformation;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.repository.guardian.GuardianRepository;
import com.rocs.infirmary.application.repository.section.SectionRepository;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.health.information.StudentHealthInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * this is the service implementation of {@code StudentHealthInformationServiceImpl}
 * */
@Service
public class StudentHealthInformationServiceImpl implements StudentHealthInformationService {
    private static Logger LOGGER = LoggerFactory.getLogger(StudentHealthInformationServiceImpl.class);
    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;
    private final GuardianRepository guardianRepository;
    /**
     * this creates a constructor for {@code StudentHealthInformationServiceImpl}
     *
     * @param studentRepository represents the student repository
     * @param sectionRepository represents the section repository
     * */
    @Autowired
    public StudentHealthInformationServiceImpl(StudentRepository studentRepository, SectionRepository sectionRepository, GuardianRepository guardianRepository) {
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.guardianRepository = guardianRepository;
    }

    @Override
    public Student updateStudentHealthInformation(StudentHealthInformation studentHealthInformation) throws StudentNotFoundException{
        Student student = studentHealthInformation.getStudent();
        if(student.getLrn() == null){
            throw new StudentNotFoundException("Student not found, please input valid LRN");
        }
        Student existingStudent = this.studentRepository.findStudentByLrn(student.getLrn());
        if(existingStudent == null) throw new StudentNotFoundException("Student not found");

        if(student.getPerson() != null){
            Person person = existingStudent.getPerson();
            if(student.getPerson().getFirstName() != null) person.setFirstName(student.getPerson().getFirstName());
            if (student.getPerson().getMiddleName() != null) person.setMiddleName(student.getPerson().getMiddleName());
            if (student.getPerson().getLastName() != null) person.setLastName(student.getPerson().getLastName());
            if (student.getPerson().getAge() != 0) person.setAge(student.getPerson().getAge());
            if (student.getPerson().getBirthdate() != null) person.setBirthdate(student.getPerson().getBirthdate());
            if(student.getPerson().getGender() != null) person.setGender(student.getPerson().getGender());
            if(student.getPerson().getEmail() != null) person.setEmail(student.getPerson().getEmail());
            if(student.getPerson().getAddress() != null) person.setAddress(student.getPerson().getAddress());
            if(student.getPerson().getContactNumber() != null) person.setContactNumber(student.getPerson().getContactNumber());
        }
        if(student.getSection() != null){
            updateSection(student,existingStudent.getSection());
        }
        if(student.getUser() != null){
            throw new StudentNotFoundException("Cannot Update User Details here");
        }
        if(student.getGuardian() != null){
            existingStudent.setGuardian(updateGuardian(student,existingStudent.getGuardian()));
        }
        return this.studentRepository.save(existingStudent);
    }
    private Section updateSection (Student student,Section existingSection){
        Section section = existingSection;
        if(student.getSection().getStrand() != null) section.setStrand(student.getSection().getStrand());
        if(student.getSection().getGradeLevel() != null) section.setGradeLevel(student.getSection().getGradeLevel());
        if(student.getSection().getSection() != null) section.setSection(student.getSection().getSection());
        return this.sectionRepository.save(section);
    }
    private Guardian updateGuardian(Student student, Guardian existingGuardian){
        Guardian guardian = existingGuardian;
        if(student.getGuardian().getGuardianName() != null) guardian.setGuardianName(student.getGuardian().getGuardianName());
        if(student.getGuardian().getGuardianNumber() != null) guardian.setGuardianNumber(student.getGuardian().getGuardianNumber());
        if(student.getGuardian().getGuardianAddress() != null) guardian.setGuardianAddress(student.getGuardian().getGuardianAddress());
        return this.guardianRepository.save(guardian);
    }
}
