package com.rocs.infirmary.application.domain.section;

import jakarta.persistence.*;
import lombok.Data;
/**
 * this represents the section entity that holds the information about the section
 * */
@Data
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;
    @Column(name = "adviser_id")
    private Long adviserId;
    private String strand;
    @Column(name = "grade_level")
    private String gradeLevel;
    private String section;
}
