package com.rocs.infirmary.application.dto.student.health.profile;
import lombok.Data;
import java.util.Date;

@Data
public class StudentHealthProfileDTO {

    //stud
    private Long studentId;
    private Long lrn;

    //prson
    private Long personId;
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;
    private Date birthdate;
    private String gender;
    private String email;
    private String address;
    private String contactNumber;

    //section
    private Long sectionId;
    private String strand;
    private String gradeLevel;
    private String section;

    //guardian
    private Long guardianId;
    private String guardianName;
    private String guardianAddress;
    private Long guardianNumber;


    //medicalHistory
    private Long medicalHistoryId;
    private String description;


}
