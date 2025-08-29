package com.rocs.infirmary.application.service.medication.trend.impl;

import com.rocs.infirmary.application.domain.medication.trend.MedicineAdministered;
import com.rocs.infirmary.application.repository.medication.trend.MedicineAdministeredRepository;
import com.rocs.infirmary.application.service.medication.trend.MedicineAdministeredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MedicineAdministeredServiceImpl implements MedicineAdministeredService {
    private final MedicineAdministeredRepository medicineAdministeredRepository;

    @Autowired
    public MedicineAdministeredServiceImpl(MedicineAdministeredRepository medicineAdministeredRepository) {
        this.medicineAdministeredRepository = medicineAdministeredRepository;
    }

    @Override
    public List<MedicineAdministered> findAll() {
        return this.medicineAdministeredRepository.findAll();
    }
}
