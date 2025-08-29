package com.rocs.infirmary.application.domain.medicalHistory;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MEDICAL_HISTORY")
public class MedicalHistory {
  @Id
  @Column(name = "med_history_id")
  private Long medHistoryId;
  private String description;
}
