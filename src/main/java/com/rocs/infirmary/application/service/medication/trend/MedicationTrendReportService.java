package com.rocs.infirmary.application.service.medication.trend;

import com.rocs.infirmary.application.domain.medicine.Medicine;
import com.rocs.infirmary.application.domain.medicine.administered.MedicineAdministered;
import com.rocs.infirmary.application.domain.medicine.trend.report.MedicineTrendReport;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
/**
 * {@code MedicationTrendReportService} is an interface of the MedicationTrendReportService
 * */
public interface MedicationTrendReportService {
    /**
     * generate the medication trend report
     *
     *@param startDate the start date of the report
     *@param endDate   the end date of the report
     * */
    List<MedicineTrendReport> generateMedicationTrendReport(Date startDate, Date endDate) throws ParseException;
    /**
     * find medicine by its id
     *
     * @param id is the unique identifier of the medicine
     * */
    Medicine findMedicineById(Long id);
    /**
     * find medicine administered between start date and end date
     *
     * @param startDate the start date where the medicine are administered
     * @param endDate the end date where the medicine are administered
     * */
    List<MedicineAdministered> findMedicineAdministeredBetween(Date startDate, Date endDate);
}
