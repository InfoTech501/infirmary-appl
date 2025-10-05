package com.rocs.infirmary.application.repository.medical.record;

import com.rocs.infirmary.application.domain.medical.record.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * {@code MedicalRecordRepository} is an interface of Medical Record repository
 * */
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findMedicalRecordByStudentIdAndIsActive(Long studentId,int isActive);
    List<MedicalRecord> findByIsActive(int isActive);

    /**
     * Retrieves a list of {@link MedicalRecord} entities where the visit date falls
     * between the specified start and end dates.
     *
     * @param startDate the beginning date of the visit date range
     * @param endDate the ending date of the visit date range
     * @return a list of medical records with visit dates between {@code startDate} and {@code endDate}
     */
    List<MedicalRecord> findByVisitDateBetween(Date startDate, Date endDate);
}
