package com.rocs.infirmary.application.service.dashboard.monthly.ailment.impl;

import com.rocs.infirmary.application.domain.medical.record.MedicalRecord;
import com.rocs.infirmary.application.domain.medical.MonthlyAilmentReport.MonthlyAilmentReport;
import com.rocs.infirmary.application.repository.medical.record.MedicalRecordRepository;
import com.rocs.infirmary.application.exception.domain.MonthlyAilmentReportException;
import com.rocs.infirmary.application.service.dashboard.monthly.ailment.MonthlyAilmentReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A service implementation that handles the generation of common monthly ailment report
 */
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
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return JSON-compatible list or a message map
     */
    @Override
    public List<MonthlyAilmentReport> generateMonthlyAilmentsReport(String startDate, String endDate) {
        try {
            LocalDate startDateStr = LocalDate.parse(startDate);
            LocalDate endDateStr = LocalDate.parse(endDate);
            Date start = Date.from(startDateStr.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(endDateStr.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
            List<MedicalRecord> records = medicalRecordRepository.findByVisitDateBetween(start, end);

            if (records.isEmpty()) {
                LOGGER.warn("No medical record was found. {}/{}", startDate,endDate);
                throw new MonthlyAilmentReportException("No medical records found");
            }

            Map<String, Long> ailmentCounts = processAilmentCounts(records);
            return generateReportItems(ailmentCounts);

        } catch (Exception e) {
            LOGGER.error("Error generating monthly ailment report", e);
            throw new MonthlyAilmentReportException("An error occurred while generating report.", e);
        }
    }

    private Map<String, Long> processAilmentCounts(List<MedicalRecord> records) {
        return records.stream().filter(record ->record.getAilments() != null && record.getAilments().getDescription() !=null)
                .collect(Collectors.groupingBy(r -> r.getAilments().getDescription(),Collectors.counting()));
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

