package com.rocs.infirmary.application.domain.person.student;

import com.rocs.infirmary.application.domain.medical.history.MedicalHistory;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import jakarta.persistence.*;
import lombok.Data;
/**
 * {@code Student} represents a student in the system.
 * It stores details like LRN (student number), personal info,
 * section details, and guardian information.
 */
@Data
@Entity
@Table(name = "student")
public class Student extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lrn;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_section_id")
    private Section section;

    @Column(name = "stud_guardian_id")
    private Long guardianId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medical_history_id")
    private MedicalHistory medicalHistory = new MedicalHistory();
}
