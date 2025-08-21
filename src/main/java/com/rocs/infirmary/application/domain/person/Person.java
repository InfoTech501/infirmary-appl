package com.rocs.infirmary.application.domain.person;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String firstName;
    @Column(length = 35)
    private String middleName;
    @Column(length = 35)
    private String lastName;
    private int age;
    private Date birthdate;
    private String gender;
    private String email;
    private String address;
    private String contactNumber;
}
