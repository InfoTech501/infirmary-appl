package com.rocs.infirmary.application.domain.ailment;

import jakarta.persistence.*;
import lombok.Data;
/**
 * this represents the Ailments entity which holds information about the ailments
 * */
@Entity
@Data
public class Ailments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ailment_id")
    private Long id;
    private String description;
}
