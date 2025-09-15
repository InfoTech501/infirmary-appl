package com.rocs.infirmary.application.domain.section;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;
    private String strand;
    @Column(name = "grade_level")
    private String gradeLevel;
    private String section;

}
