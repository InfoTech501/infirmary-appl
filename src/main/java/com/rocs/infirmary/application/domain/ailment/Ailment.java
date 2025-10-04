package com.rocs.infirmary.application.domain.ailment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code ailments} represents a specific medical ailment or condition that can be associated with a medical record
 * */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ailments")
public class Ailment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ailment_id")
  private Long ailmentId;
  private String description;
}
