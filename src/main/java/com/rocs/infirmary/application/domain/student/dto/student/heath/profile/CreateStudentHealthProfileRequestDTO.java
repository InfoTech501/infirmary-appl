package com.rocs.infirmary.application.domain.student.dto.student.heath.profile;

import lombok.Data;

import java.util.Date;
@Data
public class CreateStudentHealthProfileRequestDTO {
    private Long lrn;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthdate;
    private String gradeLevel;
    private String section;
    private String contactNumber;
    private String email;
    private String address;
    private String description;
}


