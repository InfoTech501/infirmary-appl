package com.rocs.infirmary.application.repository.report.monthlyAilment;

import com.rocs.infirmary.application.domain.medicalRecord.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
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
     * Finds a list of medical records that fall within a specified date range and are marked as active.
     * <p>
     * Spring Data JPA automatically generates the query for this method based on its name.
     * The method translates to a SQL query similar to:
     * {@code SELECT * FROM medical_records WHERE visit_date BETWEEN ? AND ? AND is_active = ?}
     * </p>
     *
     * @param startDate The beginning of the date range (inclusive).
     * @param endDate The end of the date range (inclusive).
     * @param isActive The status flag (e.g., 1 for active) to filter records by.
     * @return A list of matching {@link MedicalRecord} entities.
     */
    List<MedicalRecord> findByVisitDateBetweenAndIsActive(LocalDateTime startDate, LocalDateTime endDate, Integer isActive);

    /**
     * Counts the number of medical records that fall within a specified date range and are marked as active.
     * <p>
     * This method is optimized to return a single count rather than fetching the full list of records,
     * which is more efficient for large datasets.
     * </p>
     *
     * @param startDate The beginning of the date range (inclusive).
     * @param endDate The end of the date range (inclusive).
     * @param isActive The status flag (e.g., 1 for active) to filter records by.
     * @return The number of matching records as a {@link Long}.
     */
    Long countByVisitDateBetweenAndIsActive(LocalDateTime startDate, LocalDateTime endDate, Integer isActive);
}
