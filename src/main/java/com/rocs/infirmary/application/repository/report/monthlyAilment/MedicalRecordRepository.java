package com.rocs.infirmary.application.repository.report.monthlyAilment;

import com.rocs.infirmary.application.domain.medicalRecord.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


/**
 * Repository interface for managing and querying {@link MedicalRecord} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD (Create, Read, Update, Delete)
 * operations for {@link MedicalRecord} objects. It also includes custom query methods for specific
 * reporting needs, leveraging Spring Data JPA's derived query capabilities.
 * </p>
 */
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
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
