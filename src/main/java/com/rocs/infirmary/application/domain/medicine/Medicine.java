package com.rocs.infirmary.application.domain.medicine;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long id;
    @Column(name = "item_name")
    private String itemName;
    private String description;
    @Column(name = "is_available")
    private int isAvailable;
}
