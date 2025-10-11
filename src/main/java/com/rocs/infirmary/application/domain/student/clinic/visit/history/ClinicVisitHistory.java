package com.rocs.infirmary.application.domain.student.clinic.visit.history;

import lombok.Data;

import java.util.Date;

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
