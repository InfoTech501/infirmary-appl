package com.rocs.infirmary.application.repository.medicine;

import com.rocs.infirmary.application.domain.medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    Medicine findMedicineById(Long id);
}
