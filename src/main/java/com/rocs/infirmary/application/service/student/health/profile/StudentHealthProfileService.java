package com.rocs.infirmary.application.service.student.health.profile;


import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileDTO;

public interface StudentHealthProfileService {

    /**
     * finds a student health profile by id
     * @param id is the unique identifier of the student
     * */
    StudentHealthProfileDTO getStudentHealthProfileById(Long id);


}