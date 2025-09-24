package com.rocs.infirmary.application.service.student.profile.impl;


import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.repository.medical.history.MedicalHistoryRepository;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.profile.StudentHealthProfileService;
import com.rocs.infirmary.application.service.student.profile.exceptions.MedicalHistoryNotFoundException;
import com.rocs.infirmary.application.service.student.profile.exceptions.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service class that handles the student's health profile.
 */
@Service
public class StudentHealthProfileServiceImpl implements StudentHealthProfileService {

    private static Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileServiceImpl.class);
    private StudentRepository studentRepository;
    private MedicalHistoryRepository medicalHistoryRepository;

    /**
     * Constructor for StudentHealthProfileServiceImpl.
     * @param studentRepository used to access student's data
     * @param medicalHistoryRepository used to access medical history data
     */

    @Autowired
    public StudentHealthProfileServiceImpl(StudentRepository studentRepository, MedicalHistoryRepository medicalHistoryRepository) {
        this.studentRepository = studentRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    /**
     * Finds the health profile of a student by their ID.
     * @param id is the unique identifier of the student
     * @return student with all the details and medical history
     */

    @Override
    public Student findStudentHealthProfileById(Long id) {

        Student student = studentRepository.findStudentById(id);

        if (student == null) {
            LOGGER.error("student not found");
            throw new StudentNotFoundException("Student Not Found!");
        }
        List<MedicalHistory> medicalHistories = student.getMedicalHistories();
        if (medicalHistories == null || medicalHistories.isEmpty()) {
            LOGGER.error("No medical history");
            throw new MedicalHistoryNotFoundException("No medical history available");
        }
        return student;
    }
}
