package com.rocs.infirmary.application.repository.medicine;

import com.rocs.infirmary.application.domain.medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * {@code MedicineRepository} is an interface of Medicine repository
 * */
public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    Medicine findMedicineById(Long id);
}
