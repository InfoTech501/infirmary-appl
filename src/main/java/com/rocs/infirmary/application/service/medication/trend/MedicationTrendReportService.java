package com.rocs.infirmary.application.service.medication.trend;

import com.rocs.infirmary.application.domain.medicine.Medicine;
import com.rocs.infirmary.application.domain.medicine.administered.MedicineAdministered;
import com.rocs.infirmary.application.domain.medicine.trend.report.MedicineTrendReport;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface MedicationTrendReportService {
    List<MedicineAdministered> findAll();
    List<MedicineTrendReport> generateMedicationTrendReport(Date startDate, Date endDate) throws ParseException;
    Medicine findMedicineById(Long id);
    List<MedicineAdministered> findMedicineAdministeredBetween(Date startDate, Date endDate);
}
