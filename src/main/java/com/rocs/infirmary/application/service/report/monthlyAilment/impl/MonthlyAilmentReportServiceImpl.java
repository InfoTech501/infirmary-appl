package com.rocs.infirmary.application.service.report.monthlyAilment.impl;

import com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.MonthlyAilmentReportDto;
import com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.request.MonthlyAilmentsReportRequestDto;
import com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.response.MonthlyAilmentsReportResponseDto;
import com.rocs.infirmary.application.domain.medicalRecord.MedicalRecord;
import com.rocs.infirmary.application.exception.domain.MonthlyAilmentReportException;
import com.rocs.infirmary.application.repository.report.monthlyAilment.MedicalRecordRepository;
import com.rocs.infirmary.application.service.report.monthlyAilment.MonthlyAilmentReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation for monthly ailment reports.
 * <p>
 * This class handles the business logic for creating a report of the most frequent ailments
 * for a given month and year.
 * </p>
 */
@Slf4j
@Service
public class MonthlyAilmentReportServiceImpl implements MonthlyAilmentReportService {
    @Autowired
    private final MedicalRecordRepository medicalRecordRepository;

    private static final int ACTIVE_STATUS = 1;

    /**
     * Constructs a new MonthlyAilmentReportServiceImpl with the required repository dependency.
     *
     * @param medicalRecordRepository The repository for accessing medical record data.
     */
    public MonthlyAilmentReportServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    /**
     * Generates a monthly report of the most frequent ailments based on a given year, month, and an optional limit.
     * The report includes the total number of medical records for the period and a list of ailments sorted by frequency.
     * Each ailment entry includes its ID, name, frequency (number of occurrences), percentage of the total, and its rank.
     *
     * @param request The {@link MonthlyAilmentsReportRequestDto} containing the year, month, and an optional limit.
     * @return A {@link MonthlyAilmentsReportResponseDto} containing the generated report data. Returns an empty report if no data is found for the specified period.
     */
    @Override
    public MonthlyAilmentsReportResponseDto generateMonthlyAilmentsReport(MonthlyAilmentsReportRequestDto request) {
        log.info("Generating monthly ailments report for {}/{}", request.getMonth(), request.getYear());
        try {
            LocalDateTime[] dateRange = calculateDateRange(request);
            LocalDateTime startDate = dateRange[0];
            LocalDateTime endDate = dateRange[1];

            Long totalRecords = medicalRecordRepository.countByVisitDateBetweenAndIsActive(startDate, endDate, ACTIVE_STATUS);

            if (totalRecords == 0) {
                log.warn("No data available for {}/{}", request.getMonth(), request.getYear());
                return createEmptyResponse(request);
            }

            List<MedicalRecord> records = fetchRecords(startDate, endDate);
            Map<Long, List<MedicalRecord>> groupedByAilment = groupRecordsByAilment(records);
            List<MonthlyAilmentReportDto> ailments = mapToReportDtos(groupedByAilment, totalRecords);
            applyLimitAndRank(ailments, request.getLimit());

            String monthName = Month.of(request.getMonth()).name();
            log.info("Successfully generated report with {} ailments", ailments.size());

            return new MonthlyAilmentsReportResponseDto(request.getYear(),request.getMonth(), monthName, totalRecords, ailments);

        }catch (DataAccessException e) {
            log.error("Database error while generating monthly ailments report",e);
            throw new MonthlyAilmentReportException("Failed to retrieve data from the database.",e);
        } catch (Exception e) {
            log.error("An error occurred while generating monthly ailments report",e);
            throw new MonthlyAilmentReportException("An unexpected error occurred.",e);
        }
    }

    //helper methods
    private LocalDateTime[] calculateDateRange(MonthlyAilmentsReportRequestDto request) {
        LocalDateTime start = LocalDateTime.of(request.getYear(), request.getMonth(), 1, 0, 0);
        LocalDateTime end = start.plusMonths(1).minusSeconds(1);
        return new LocalDateTime[]{start, end};
    }

    private List<MedicalRecord> fetchRecords(LocalDateTime start, LocalDateTime end) {
        return medicalRecordRepository.findByVisitDateBetweenAndIsActive(start, end, ACTIVE_STATUS);
    }

    private Map<Long, List<MedicalRecord>> groupRecordsByAilment(List<MedicalRecord> records) {
        return records.stream().collect(Collectors.groupingBy(record -> record.getAilment().getAilmentId()));
    }

    private List<MonthlyAilmentReportDto> mapToReportDtos(Map<Long, List<MedicalRecord>> grouped, Long total) {
        return grouped.entrySet().stream()
                .map(entry -> {
                    Long ailmentId = entry.getKey();
                    List<MedicalRecord> ailmentRecords = entry.getValue();
                    String ailmentName = ailmentRecords.getFirst().getAilment().getDescription();
                    Long frequency = (long) ailmentRecords.size();
                    double percentage = Math.round((frequency.doubleValue() / total) * 10000.0) / 100.0;

                    return new MonthlyAilmentReportDto(ailmentId, ailmentName, frequency, percentage);
                }).sorted(Comparator.comparing(MonthlyAilmentReportDto::getFrequency).reversed().thenComparing(MonthlyAilmentReportDto::getAilmentName)).collect(Collectors.toList());
    }

    private void applyLimitAndRank(List<MonthlyAilmentReportDto> ailments, Integer limit) {
        if (limit != null && limit > 0 && ailments.size() > limit) {
            ailments.subList(limit, ailments.size()).clear();
        }

        for (int i = 0; i < ailments.size(); i++) {
            ailments.get(i).setRankOrder(i + 1);
        }
    }

    private MonthlyAilmentsReportResponseDto createEmptyResponse(MonthlyAilmentsReportRequestDto request) {
        String monthName = Month.of(request.getMonth()).name();
        return new MonthlyAilmentsReportResponseDto(request.getYear(), request.getMonth(), monthName, 0L, List.of());
    }
}
