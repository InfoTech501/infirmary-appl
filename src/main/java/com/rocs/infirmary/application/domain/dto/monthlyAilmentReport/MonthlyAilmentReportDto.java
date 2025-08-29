package com.rocs.infirmary.application.domain.dto.monthlyAilmentReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) for representing a single ailment's data within the monthly report.
 * This class holds the processed information for a specific ailment, including its ID, name,
 * frequency of occurrence, calculated percentage of total records, and its rank.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyAilmentReportDto {
    private Long ailmentId;
    private String ailmentName;
    private Long frequency;
    private Double percentage;
    private Integer rankOrder;

    /**
     * Constructs a new MonthlyAilmentReportDto with basic ailment information.
     *
     * @param ailmentId   The unique identifier of the ailment.
     * @param ailmentName The name or description of the ailment.
     * @param frequency   The total number of times this ailment was recorded.
     * @param percentage  The percentage of total records this ailment represents.
     */
    public MonthlyAilmentReportDto(Long ailmentId, String ailmentName, Long frequency, Double percentage) {
        this.ailmentId = ailmentId;
        this.ailmentName = ailmentName;
        this.frequency = frequency;
        this.percentage = percentage;
    }
}
