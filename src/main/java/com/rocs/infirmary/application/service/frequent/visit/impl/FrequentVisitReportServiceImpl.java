package com.rocs.infirmary.application.service.frequent.visit.impl;

import com.rocs.infirmary.application.domain.medicalRecord.FrequentVisitReport.FrequentVisitReport;
import com.rocs.infirmary.application.domain.medicalRecord.MedicalRecord;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.exception.domain.FrequentVisitReportException;
import com.rocs.infirmary.application.repository.frequent.visit.MedicalRecordRepository;
import com.rocs.infirmary.application.repository.section.SectionRepository;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.frequent.visit.FrequentVisitReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FrequentVisitReportServiceImpl implements FrequentVisitReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrequentVisitReportServiceImpl.class);
    private final MedicalRecordRepository medicalRecordRepository;

    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public FrequentVisitReportServiceImpl(MedicalRecordRepository medicalRecordRepository, StudentRepository studentRepository, SectionRepository sectionRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<FrequentVisitReport> generateFrequentVisitReport(String startDate, String endDate) {
        try {
            LocalDate startDateStr = LocalDate.parse(startDate);
            LocalDate endDateStr = LocalDate.parse(endDate);
            Date start = Date.from(startDateStr.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(endDateStr.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
            List<MedicalRecord> records = medicalRecordRepository.findByVisitDateBetween(start, end);

            if (records.isEmpty()) {
                LOGGER.warn("No medical record was found. {}/{}", startDate, endDate);
                return new ArrayList<>();
            }

            Map<Long, Long> visitCounts = records.stream().filter(record -> record.getStudentId() != null).collect(Collectors.groupingBy(MedicalRecord::getStudentId, Collectors.counting()));
            return generateReportItems(visitCounts, start, end);

        } catch (Exception e) {
            LOGGER.error("Error generating frequent visit report", e);
            throw new FrequentVisitReportException("An error occurred while generating frequent visit report.", e);
        }
    }

    private List<FrequentVisitReport> generateReportItems(Map<Long, Long> visitCounts, Date start, Date end) {
        List<Map.Entry<Long, Long>> sortedEntries = new ArrayList<>(visitCounts.entrySet());
        sortedEntries.sort(Map.Entry.<Long, Long>comparingByValue().reversed());

        List<FrequentVisitReport> reportItems = new ArrayList<>();
        int rank = 1;

        for (Map.Entry<Long, Long> entry : sortedEntries) {
            Long studentId = entry.getKey();
            Long visitCount = entry.getValue();

            Student student = studentRepository.findStudentByUserId(studentId);
            Section section = student != null ? student.getSection() : null;

            String fullName = "Student #" + studentId;
            if (student != null && student.getPerson() != null) {
                List<String> nameParts = new ArrayList<>();
                if (student.getPerson().getFirstname() != null && !student.getPerson().getFirstname().isBlank()) {
                    nameParts.add(student.getPerson().getFirstname().trim());
                }
                if (student.getPerson().getMiddlename() != null && !student.getPerson().getMiddlename().isBlank()) {
                    nameParts.add(student.getPerson().getMiddlename().trim());
                }
                if (student.getPerson().getLastname() != null && !student.getPerson().getLastname().isBlank()) {
                    nameParts.add(student.getPerson().getLastname().trim());
                }
                if (!nameParts.isEmpty()) {
                    fullName = String.join(" ", nameParts);
                }
            }

            FrequentVisitReport visitReport = new FrequentVisitReport();
            visitReport.setRank(rank++);
            visitReport.setFullName(fullName);
            visitReport.setAge(student != null && student.getPerson() != null ? student.getPerson().getAge() : null);
            visitReport.setSection(section != null ? section.getSection() : null);
            visitReport.setNoOfVisit(visitCount);
            reportItems.add(visitReport);
        }

        return reportItems;
    }
}