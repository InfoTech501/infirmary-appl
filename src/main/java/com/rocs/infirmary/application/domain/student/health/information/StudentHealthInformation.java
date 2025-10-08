package com.rocs.infirmary.application.domain.student.health.information;

import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import jakarta.persistence.*;
import lombok.Data;
/**
 * This class represents the student's health information.
 */
@Data
public class StudentHealthInformation {
    @OneToMany
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;
}
