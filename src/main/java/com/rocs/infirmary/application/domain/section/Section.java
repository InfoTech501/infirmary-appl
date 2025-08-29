package com.rocs.infirmary.application.domain.section;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "adviser_id")
    private Long adviserId;

    @Column(name = "strand")
    private String strand;

    @Column(name = "grade_level")
    private String gradeLevel;

    @Column(name = "section")
    private String section;
}
