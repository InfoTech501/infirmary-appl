package com.rocs.infirmary.application.repository.medical.record;

import com.rocs.infirmary.application.domain.medical.record.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code MedicalRecordRepository} is an interface of Medical Record repository
 * */
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findMedicalRecordByStudentIdAndIsActive(Long studentId,int isActive);
    List<MedicalRecord> findByIsActive(int isActive);
}
