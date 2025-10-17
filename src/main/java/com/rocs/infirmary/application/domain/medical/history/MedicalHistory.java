package com.rocs.infirmary.application.domain.medical.history;

import jakarta.persistence.*;
import lombok.Data;

/**
 * {@code medical_history} represents the patient's medical history entry
 * */
@Entity
@Data
@Table(name = "medical_history")
public class MedicalHistory {
  @Id
  @Column(name = "med_history_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long medHistoryId;
  private String description;
}