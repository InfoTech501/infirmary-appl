package com.rocs.infirmary.application.service.report.monthlyAilment.impl;

import com.rocs.infirmary.application.domain.medicalRecord.MedicalRecord;
import com.rocs.infirmary.application.domain.medicalRecord.MonthlyAilmentReport.MonthlyAilmentReport;
import com.rocs.infirmary.application.repository.report.monthlyAilment.MedicalRecordRepository;
import com.rocs.infirmary.application.exception.domain.MonthlyAilmentReportException;
import com.rocs.infirmary.application.service.report.monthlyAilment.MonthlyAilmentReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MonthlyAilmentReportServiceImpl implements MonthlyAilmentReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyAilmentReportServiceImpl.class);
    private final MedicalRecordRepository medicalRecordRepository;

    /**
     * Constructs a new instance with the required {@link MedicalRecordRepository}.
     * @param medicalRecordRepository repository for accessing medical records
     */
    @Autowired
    public MonthlyAilmentReportServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    /**
     * Generates a monthly ailment report with ailment descriptions and student counts.
     * @param month The target month (1-12)
     * @param year The target year (e.g., 2025)
     * @return JSON-compatible list or a message map
     */
    @Override
    public List<MonthlyAilmentReport> generateMonthlyAilmentsReport(int month, int year) {
        LOGGER.info("Generating monthly ailments report for {}/{}", month, year);

        try {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

            List<MedicalRecord> records = medicalRecordRepository.findByVisitDateBetween(start, end);

            if (records.isEmpty()) {
                LOGGER.warn("No medical record was found. {}/{}", month,year );
                return new ArrayList<>();
            }

            Map<String, Long> ailmentCounts = processAilmentCounts(records);
            return generateReportItems(ailmentCounts);

        } catch (Exception e) {
            LOGGER.error("Error generating monthly ailment report", e);
            throw new MonthlyAilmentReportException("An error occurred while generating report.", e);
        }
    }

    private Map<String, Long> processAilmentCounts(List<MedicalRecord> records) {
        return records.stream().filter(record ->record.getAilment() != null && record.getAilment().getDescription() !=null)
                .collect(Collectors.groupingBy(r -> r.getAilment().getDescription(),Collectors.counting()));
    }

    private List<MonthlyAilmentReport> generateReportItems(Map<String, Long> ailmentCounts) {
        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(ailmentCounts.entrySet());
        sortedEntries.sort(Map.Entry.<String, Long>comparingByValue().reversed());

        List<MonthlyAilmentReport> reportItems = new ArrayList<>();
        int rank = 1;

        for (Map.Entry<String, Long> entry : sortedEntries) {
            MonthlyAilmentReport reportRecord = new MonthlyAilmentReport();
            reportRecord.setRank(rank++);
            reportRecord.setIllness(entry.getKey());
            reportRecord.setNoOfStudents(entry.getValue());
            reportItems.add(reportRecord);
        }

        return reportItems;
    }
}

