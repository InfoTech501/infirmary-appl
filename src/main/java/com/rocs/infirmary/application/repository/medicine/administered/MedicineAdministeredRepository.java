package com.rocs.infirmary.application.repository.medicine.administered;

import com.rocs.infirmary.application.domain.medicine.administered.MedicineAdministered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
/**
 * {@code MedicineAdministeredRepository} is an interface of Medicine administered repository
 * */
public interface MedicineAdministeredRepository extends JpaRepository<MedicineAdministered,Long> {
    List<MedicineAdministered> findAll();
    List<MedicineAdministered> findByDateAdministeredBetween(Date startDate, Date endDate);
}
