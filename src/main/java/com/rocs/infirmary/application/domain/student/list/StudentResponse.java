package com.rocs.infirmary.application.domain.student.list;

import lombok.Data;

/**
 * data transfer object for studentslist
 * */
@Data
public class StudentResponse {

    private Long lrn;
    private String firstName;
    private String lastName;
    private int age;
    private String section;
    private String gradeLevel;
    private String guardianName;

}

