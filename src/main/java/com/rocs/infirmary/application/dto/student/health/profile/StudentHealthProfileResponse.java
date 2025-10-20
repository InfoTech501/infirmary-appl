package com.rocs.infirmary.application.dto.student.health.profile;
import com.rocs.infirmary.application.domain.student.Student;

import lombok.Data;

/**
 *Data Transfer Object used to return a student's health profile information
 */
@Data
public class StudentHealthProfileResponse {

    private Student student;


}