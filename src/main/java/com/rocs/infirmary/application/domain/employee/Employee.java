package com.rocs.infirmary.application.domain.employee;

import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
/**
 * this holds the credential of the employee
 * */
@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "employee_number")
    private int employeeNumber;
    @Column(name = "date_employed")
    private Date dateEmployed;
    @Column(name = "employment_status")
    private String employmentStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
