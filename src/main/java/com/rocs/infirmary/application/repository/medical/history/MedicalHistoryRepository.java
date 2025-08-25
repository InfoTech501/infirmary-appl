package com.rocs.infirmary.application.repository.medical.history;

import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory,Long> {
}
