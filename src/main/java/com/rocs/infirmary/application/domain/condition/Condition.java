package com.rocs.infirmary.application.domain.condition;

import jakarta.persistence.*;
import lombok.Data;
/**
 * this represents the entity for diagnose condition which contains details for students diagnosed conditions
 * */
@Data
@Entity(name = "diagnosed_condition")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id")
    private Long id;

    @Column(name = "condition_name")
    private String conditionName;
}
