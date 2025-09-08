package com.rocs.infirmary.application.domain.medicine.trend.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class MedicineTrendReport{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    private int rank;
    private int trendOvertime;
    private int totalDistributedMedicine;
    private String medicineName;
    private int distributedMedicine;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd yyyy")
    private List<Date> dateAdministered;
}
