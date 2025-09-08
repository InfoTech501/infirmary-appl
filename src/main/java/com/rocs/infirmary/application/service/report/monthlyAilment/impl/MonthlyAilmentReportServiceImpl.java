package com.rocs.infirmary.application.service.report.monthlyAilment.impl;

import com.rocs.infirmary.application.domain.medicalRecord.MedicalRecord;
import com.rocs.infirmary.application.repository.report.monthlyAilment.MedicalRecordRepository;
import com.rocs.infirmary.application.exception.domain.MonthlyAilmentReportException;
import com.rocs.infirmary.application.service.login.attempts.LoginAttemptsService;
import com.rocs.infirmary.application.service.report.monthlyAilment.MonthlyAilmentReportService;
import lombok.extern.slf4j.Slf4j;
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
    public Object generateMonthlyAilmentsReport(int month, int year) {
        LOGGER.info("Generating monthly ailments report for {}/{}", month, year);

        try {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

            List<MedicalRecord> records = medicalRecordRepository.findByVisitDateBetween(start, end);

            if (records.isEmpty()) {return Map.of("message", "No data available.");}

            Map<String, Long> ailmentCounts = records.stream().filter(record -> record.getAilment() != null && record.getAilment().getDescription() != null).collect(Collectors.groupingBy(
                            r -> r.getAilment().getDescription(), Collectors.counting()));
            int[] rank = {1};
            return ailmentCounts.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .map(entry -> {
                        Map<String, Object> item = new HashMap<>();
                        item.put("rank", rank[0]++);
                        item.put("illness", entry.getKey());
                        item.put("noOfStudents", entry.getValue());
                        return item;
                    }).collect(Collectors.toList());

        } catch (Exception e) {
            LOGGER.error("Error generating monthly ailment report", e);
            throw new MonthlyAilmentReportException("An error occurred while generating report.", e);
        }
    }
}

