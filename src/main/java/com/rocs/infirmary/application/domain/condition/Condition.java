package com.rocs.infirmary.application.domain.condition;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diagnosed_condition")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "condition_name")
    private String conditionName;
}
