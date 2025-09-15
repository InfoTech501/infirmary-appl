package com.rocs.infirmary.application.domain.employee;

import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @Column(name="employee_id")
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
