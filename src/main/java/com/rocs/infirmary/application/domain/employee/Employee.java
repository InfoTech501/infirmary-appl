package com.rocs.infirmary.application.domain.employee;

import com.rocs.infirmary.application.domain.department.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * {@code employee} represents a staff member in the infirmary system,
 * linking to personal and departmental details.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "employee_number")
    private Long employeeNumber;

    @Column(name = "date_employed")
    private Date dateEmployed;

    @Column(name = "employment_status")
    private String employmentStatus;

    @OneToOne
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;
}