package com.rocs.infirmary.application.domain.medical.history;

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
}
