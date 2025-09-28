package com.rocs.infirmary.application.service.clinic.visit.history;

import com.rocs.infirmary.application.domain.clinic.visit.history.ClinicVisitHistory;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;

import java.util.List;
/**
 * {@code ClinicVisitHistoryService} is an Interface of ClinicVisitHistoryServiceImpl
 * */
public interface ClinicVisitHistoryService {
    /**
     * this finds the specific student clinic visit
     *
     * @param lrn is used as a unique identifier for each student
     * */
    List<ClinicVisitHistory> findClinicVisitByStudentLrn(Long lrn) throws StudentNotFoundException;
    /**
     * this finds all student clinic visit
     * */
    List<ClinicVisitHistory> findAllClinicVisits();
}
