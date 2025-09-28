package com.rocs.infirmary.application.domain.clinic.visit.history;

import com.rocs.infirmary.application.domain.medical.record.MedicalRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
import java.util.List;
/**
 * this is used to handle or information for clinic visit history
 * */
@Data
public class ClinicVisitHistory {
   private Long id;
   private String studentName;
   private Long lrn;
   private String ailment;
   private String nurseInCharge;
   private String symptoms;
   private String temperatureReadings;
   private String bloodPressure;
   private Integer pulseRate;
   private Integer respiratoryRate;
   private Date visitDate;
}
