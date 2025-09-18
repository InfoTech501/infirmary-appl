package com.rocs.infirmary.application.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.utils.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * {@code User} represent the user entity. This hold the login information of this user.
 * */
@Entity(name = "login")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String userId;
    private String username;
    private String password;
    private Date lastLoginDate;
    private Date joinDate;
    private String role;

    @OneToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    @JsonIgnore
    private Person person;

    @Column(name = "authorities", nullable = false)
    @Convert(converter = StringListConverter.class)
    private List<String> authorities;
    private boolean isActive;
    private boolean isLocked;
}
