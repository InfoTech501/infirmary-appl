package com.rocs.infirmary.application.domain.medical.history;

import com.rocs.infirmary.application.domain.student.Student;

import jakarta.persistence.*;
import lombok.Data;



/**
 * {@code MedicalHistory} represents the medical history of a student.
 */

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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;





}