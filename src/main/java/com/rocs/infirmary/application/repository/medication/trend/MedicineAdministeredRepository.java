package com.rocs.infirmary.application.repository.medication.trend;

import com.rocs.infirmary.application.domain.medication.trend.MedicineAdministered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineAdministeredRepository extends JpaRepository<MedicineAdministered,Long> {
    List<MedicineAdministered> findAll();
}
