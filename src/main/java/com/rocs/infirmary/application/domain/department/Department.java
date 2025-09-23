package com.rocs.infirmary.application.domain.department;

import jakarta.persistence.*;
import lombok.Data;
/**
 * {@code Department} represents the department entity, this holds information about the department
 * */
@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "department_name")
    private String departmentName;
}
