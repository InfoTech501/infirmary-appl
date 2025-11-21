package com.rocs.infirmary.application.domain.medical.history;

import com.rocs.infirmary.application.domain.condition.Condition;
import com.rocs.infirmary.application.domain.student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
  @Column(name = "last_checkup_date")
  private Date lastCheckupDate;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "student_id")
  @JsonIgnore
  private Student student;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "condition_id")
  private Condition condition;
}
