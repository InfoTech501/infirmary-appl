package com.rocs.infirmary.application.domain.ailment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code AILMENTS} represents a specific medical ailment or condition that can be associated with a medical record
 * */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AILMENTS")
public class Ailment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ailment_id")
  private Long ailmentId;
  private String description;
}
