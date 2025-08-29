package com.rocs.infirmary.application.service.medication.trend;

import com.rocs.infirmary.application.domain.medication.trend.MedicineAdministered;

import java.util.List;

public interface MedicineAdministeredService {
    List<MedicineAdministered> findAll();
}
