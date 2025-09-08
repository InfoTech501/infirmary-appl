package com.rocs.infirmary.application.domain.medicalHistory;

import jakarta.persistence.*;
import lombok.Data;

/**
 * {@code MEDICAL_HISTORY} represents the patient's medical history entry
 * */
@Entity
@Data
@Table(name = "MEDICAL_HISTORY")
public class MedicalHistory {
  @Id
  @Column(name = "med_history_id")
  private Long medHistoryId;
  private String description;
}
