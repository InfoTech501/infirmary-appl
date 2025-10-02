package com.rocs.infirmary.application.domain.medicalRecord;

import com.rocs.infirmary.application.domain.ailment.Ailment;
import com.rocs.infirmary.application.domain.medicalHistory.MedicalHistory;
import com.rocs.infirmary.application.domain.person.Person;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * {@code medical_record} represents a medical record associated to patient, capturing visit details, vital signs, and relationships to ailments, medical history, and attending nurse.
 * */
@Entity
@Data
@Table(name = "medical_record")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ailment_id", insertable = false, updatable = false)
    private Ailment ailment;

    @ManyToOne
    @JoinColumn(name = "med_history_id", insertable = false, updatable = false)
    private MedicalHistory medicalHistory;

    @ManyToOne
    @JoinColumn(name = "nurse_in_charge_id", insertable = false, updatable = false)
    private Person nurseInCharge;

    private String symptoms;
    private String temperatureReadings;
    private Date visitDate;
    private String treatment;
    private String bloodPressure;
    private Long pulseRate;
    private Long respiratoryRate;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "ailment_id")
    private Long ailmentId;

    @Column(name = "med_history_id")
    private Long medHistoryId;

    @Column(name = "nurse_in_charge_id")
    private Long nurseInChargeId;
}
