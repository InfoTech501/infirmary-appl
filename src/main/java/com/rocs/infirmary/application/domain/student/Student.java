package com.rocs.infirmary.application.domain.student;

import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lrn;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "section_section_id")
    private Section section;

    @Column(name = "stud_guardian_id")
    private Long guardianId;

}
