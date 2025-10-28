package com.rocs.infirmary.application.domain.guardian;

import jakarta.persistence.*;
import lombok.Data;

/**
 * {@code Guardian} represents the student's guardian, this holds basic information about the guardian
 * */
@Entity
@Data
@Table(name = "guardian_details")
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guardian_id")
    private Long id;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_address")
    private String guardianAddress;

    @Column(name = "guardian_contact_number")
    private String guardianNumber;
}