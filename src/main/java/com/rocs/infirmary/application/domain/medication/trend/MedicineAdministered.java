package com.rocs.infirmary.application.domain.medication.trend;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class MedicineAdministered {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "med_administered_id")
    private Long id;
    @Column(name = "medicine_id")
    private Long medicineId;
    @Column(name = "med_record_id")
    private Long medRecordId;
    @Column(name = "nurse_in_charge_id")
    private Long nurseInChargeId;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "date_administered")
    private Date dateAdministered;
}
