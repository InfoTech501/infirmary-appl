package com.rocs.infirmary.application.service.medication.trend.impl;

import com.rocs.infirmary.application.domain.medicine.Medicine;
import com.rocs.infirmary.application.domain.medicine.administered.MedicineAdministered;
import com.rocs.infirmary.application.domain.medicine.trend.report.MedicineTrendReport;
import com.rocs.infirmary.application.exception.domain.MedicineNotFoundException;
import com.rocs.infirmary.application.repository.medicine.MedicineRepository;
import com.rocs.infirmary.application.repository.medicine.administered.MedicineAdministeredRepository;
import com.rocs.infirmary.application.service.medication.trend.MedicationTrendReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicationTrendReportServiceImpl implements MedicationTrendReportService {
    private final MedicineAdministeredRepository medicineAdministeredRepository;
    private final MedicineRepository medicineRepository;

    @Autowired
    public MedicationTrendReportServiceImpl(MedicineAdministeredRepository medicineAdministeredRepository,MedicineRepository medicineRepository) {
        this.medicineAdministeredRepository = medicineAdministeredRepository;
        this.medicineRepository = medicineRepository;
    }

    @Override
    public Medicine findMedicineById(Long id) {
        Medicine medicine = this.medicineRepository.findMedicineById(id);
        if(medicine == null){
            throw new MedicineNotFoundException("Medicine not found");
        }
        return medicine;
    }

    @Override
    public List<MedicineAdministered> findMedicineAdministeredBetween(Date startDate, Date endDate) {
        return this.medicineAdministeredRepository.findByDateAdministeredBetween(startDate, endDate);
    }

    @Override
    public List<MedicineTrendReport> generateMedicationTrendReport(Date startDate, Date endDate) throws ParseException {
        List<MedicineAdministered> medicineAdministeredList = findMedicineAdministeredBetween(startDate, endDate);
        List<MedicineTrendReport> reports = new ArrayList<>();

        Map<Long, List<MedicineAdministered>> medicineIds = medicineAdministeredList.stream().collect(Collectors.groupingBy(MedicineAdministered::getMedicineId));

        for(Map.Entry<Long, List<MedicineAdministered>> entries : medicineIds.entrySet()){
            Long medicineId = entries.getKey();
            List<MedicineAdministered> administeredList = entries.getValue();
            int totalDistributed = administeredList.stream().mapToInt(MedicineAdministered::getQuantity).sum();
            int trendOverTime = administeredList.size();
            List<Date> dateAdministered = administeredList.stream().map(MedicineAdministered::getDateAdministered).toList();
            MedicineTrendReport medicineTrendReport = new MedicineTrendReport();
            medicineTrendReport.setTotalDistributedMedicine(medicineAdministeredList.size());

            medicineTrendReport.setStartDate(startDate);
            medicineTrendReport.setEndDate(endDate);
            medicineAdministeredRank(reports);

            String itemName = findMedicineById(medicineId).getItemName();
            medicineTrendReport.setMedicineName(itemName);
            medicineTrendReport.setTrendOvertime(trendOverTime);
            medicineTrendReport.setDistributedMedicine(totalDistributed);
            medicineTrendReport.setDateAdministered(dateAdministered);
            reports.add(medicineTrendReport);
        }
        return reports;
    }

    private void medicineAdministeredRank(List<MedicineTrendReport> medicineAdministeredList){
        medicineAdministeredList.sort(Comparator.comparingInt(MedicineTrendReport::getDistributedMedicine).reversed());
        int rank = 1;
        int sameRankCount = 0;
        int previousCount = -1;

        for(MedicineTrendReport medicineTrendReport : medicineAdministeredList){
            if(medicineTrendReport.getDistributedMedicine() == previousCount){
                medicineTrendReport.setRank(rank);
                sameRankCount++;
            }else{
                rank += sameRankCount;
                medicineTrendReport.setRank(rank);
                previousCount = medicineTrendReport.getDistributedMedicine();
                sameRankCount = 1;
            }
        }
    }
}
