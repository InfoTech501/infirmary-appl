package com.rocs.infirmary.application.domain.section;

import jakarta.persistence.*;
import lombok.Data;
/**
 * {@code Section} represents a school section where students belong.
 * It stores details like adviser, strand, grade level, and section name.
 */
@Data
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;
    @Column(name = "adviser_id")
    private Long adviserId;
    private String strand;
    @Column(name = "grade_level")
    private String gradeLevel;
    private String section;
}
