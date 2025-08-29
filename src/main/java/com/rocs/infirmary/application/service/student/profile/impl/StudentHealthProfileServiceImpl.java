package com.rocs.infirmary.application.service.student.profile.impl;


import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.repository.medical.history.MedicalHistoryRepository;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.student.profile.StudentHealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Student> viewStudentHealthProfileById(Long id) {


        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            List<MedicalHistory> medicalHistories = student.get().getMedicalHistories();
            if (medicalHistories.isEmpty()) {
                System.out.println("No medical history available");
            }
        }
        return student;
    }
}

