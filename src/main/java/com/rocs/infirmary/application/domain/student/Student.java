package com.rocs.infirmary.application.domain.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long lrn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL)
    private Section section;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
