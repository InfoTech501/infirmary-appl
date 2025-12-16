package com.rocs.infirmary.application.repository.frequent.visit;

import com.rocs.infirmary.application.domain.medicalRecord.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Repository for accessing {@link MedicalRecord} data related to frequent visits.
 * Provides standard CRUD operations and a custom query for filtering records by visit date range.
 */
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    /**
     * Finds medical records with visit dates between the given start and end dates.
     *
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return list of matching medical records
     */
    List<MedicalRecord> findByVisitDateBetween(Date startDate, Date endDate);
}
