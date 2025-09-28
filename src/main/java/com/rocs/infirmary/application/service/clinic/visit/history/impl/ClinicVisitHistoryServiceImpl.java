package com.rocs.infirmary.application.service.clinic.visit.history.impl;

import com.rocs.infirmary.application.domain.clinic.visit.history.ClinicVisitHistory;
import com.rocs.infirmary.application.domain.medical.record.MedicalRecord;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.repository.medical.record.MedicalRecordRepository;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.service.clinic.visit.history.ClinicVisitHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * this is the service implementation of {@code ClinicVisitHistory}
 * */
@Service
public class ClinicVisitHistoryServiceImpl implements ClinicVisitHistoryService {
    private MedicalRecordRepository medicalRecordRepository;
    private final StudentRepository studentRepository;
    /**
     * this creates a constructor for {@code ClinicVisitHistoryServiceImpl}
     *
     * @param medicalRecordRepository represents the medical record repository
     * @param studentRepository represents the student repository
     * */
    @Autowired
    public ClinicVisitHistoryServiceImpl(MedicalRecordRepository medicalRecordRepository, StudentRepository studentRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public List<ClinicVisitHistory> findClinicVisitByStudentLrn(Long lrn) throws StudentNotFoundException {
        if(Long.toString(lrn).length() != 12){
            throw new StudentNotFoundException("Invalid LRN check if it is a 12 digit number");
        }
        Student student = this.studentRepository.findStudentByLrn(lrn);
        if(student == null){
            throw new StudentNotFoundException("Student not found");
        }
        return this.medicalRecordRepository.findMedicalRecordByStudentIdAndIsActive(student.getId(),1).stream().map(this::populateStudentClinicVisitRecordFields).toList();
    }


    @Override
    public List<ClinicVisitHistory> findAllClinicVisits(){
        return this.medicalRecordRepository.findByIsActive(1).stream().map(this::populateStudentClinicVisitRecordFields).toList();
    }
    private ClinicVisitHistory populateStudentClinicVisitRecordFields(MedicalRecord record){

        String studentMiddleName = record.getStudent().getPerson().getMiddleName() != null ? record.getStudent().getPerson().getMiddleName() : "";
        String employeeMiddleName = record.getEmployee().getPerson().getMiddleName() != null ? record.getEmployee().getPerson().getMiddleName() : "";
        ClinicVisitHistory clinicVisitHistory = new ClinicVisitHistory();

        clinicVisitHistory.setId(record.getId());
        clinicVisitHistory.setStudentName(record.getStudent().getPerson().getFirstName()+" "+
                studentMiddleName+
                record.getStudent().getPerson().getLastName()
        );
        clinicVisitHistory.setLrn(record.getStudent().getLrn());
        clinicVisitHistory.setAilment(record.getAilments().getDescription());
        clinicVisitHistory.setNurseInCharge(record.getEmployee().getPerson().getFirstName()+" "+
                employeeMiddleName +
                record.getEmployee().getPerson().getLastName()
        );
        clinicVisitHistory.setSymptoms(record.getSymptoms());
        clinicVisitHistory.setTemperatureReadings(record.getTemperatureReadings());
        clinicVisitHistory.setBloodPressure(record.getBloodPressure());
        clinicVisitHistory.setPulseRate(record.getPulseRate());
        clinicVisitHistory.setRespiratoryRate(record.getRespiratoryRate());
        clinicVisitHistory.setVisitDate(record.getVisitDate());
        return clinicVisitHistory;
    }
}
