package com.rocs.infirmary.application.domain.student.list;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

//dto for studentslist w/ fix arrangement
@Data
@JsonPropertyOrder({ "lrn", "firstName", "lastName","age", "section", "gradeLevel", "guardianName" })
public class AllStudentsList {

    private Long lrn;
    private String firstName;
    private String lastName;
    private int age;
    private String section;
    private String gradeLevel;
    private String guardianName;

}

