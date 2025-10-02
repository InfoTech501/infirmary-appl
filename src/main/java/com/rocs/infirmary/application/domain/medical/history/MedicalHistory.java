package com.rocs.infirmary.application.domain.medical.history;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    private String description;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;





}