package com.rocs.infirmary.application.domain.user.student;

import com.rocs.infirmary.application.domain.person.Person;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "STUDENT")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_id", unique = true)
    private Long personId;

    @Column(name = "section_section_id")
    private Long sectionSectionId;

    @Column(name = "stud_guardian_id")
    private Long studGuardianId;

    @Column(name = "LRN")
    private Long lrn;

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Person person;
}
