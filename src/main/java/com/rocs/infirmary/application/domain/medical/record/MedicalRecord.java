package com.rocs.infirmary.application.domain.medical.record;

import com.rocs.infirmary.application.domain.ailment.Ailments;
import com.rocs.infirmary.application.domain.employee.Employee;
import com.rocs.infirmary.application.domain.student.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
/**
 * this represents the MedicalRecord entity which holds information about medical records
 * */
@Entity
@Data
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "ailment_id",referencedColumnName = "ailment_id")
    private Ailments ailments;
    @ManyToOne
    @JoinColumn(name = "nurse_in_charge_id",referencedColumnName = "id")
    private Employee employee;
    private String symptoms;
    @Column(name = "temperature_readings")
    private String temperatureReadings;
    @Column(name = "blood_pressure")
    private String bloodPressure;
    @Column(name = "pulse_rate")
    private Integer pulseRate;
    @Column(name = "respiratory_rate")
    private Integer respiratoryRate;
    @Column(name = "visit_date")
    private Date visitDate;
    @Column(name = "is_active")
    private int isActive;

}
