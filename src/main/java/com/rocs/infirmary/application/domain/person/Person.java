package com.rocs.infirmary.application.domain.person;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    private int age;
    private Date birthdate;
    private String gender;
    private String email;
    private String address;
    @Column(name = "contact_number")
    private String contactNumber;
}
