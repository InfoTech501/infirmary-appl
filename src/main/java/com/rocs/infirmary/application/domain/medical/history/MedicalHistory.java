package com.rocs.infirmary.application.domain.medical.history;

import com.rocs.infirmary.application.domain.student.Student;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medical_history")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "med_history_id")
    private Long medHistoryId;


    @Column(name = "description")
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}