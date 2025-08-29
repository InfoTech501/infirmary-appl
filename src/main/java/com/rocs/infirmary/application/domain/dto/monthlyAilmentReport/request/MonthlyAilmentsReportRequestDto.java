package com.rocs.infirmary.application.domain.dto.monthlyAilmentReport.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


/**
 * Data Transfer Object (DTO) for requesting a monthly ailments report.
 * This class defines the parameters required by the client to generate the report,
 * including validation constraints for the year, month, and an optional limit.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyAilmentsReportRequestDto {
    @NonNull
    @Min(value = 1900, message = "Year must be >= 1900")
    @Max(value = 9999, message = "Year must be <= 9999")
    private Integer year;

    @NonNull
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;

    @Min(value = 1, message = "Limit must be at least 1")
    private Integer limit = 10;

}
