package com.rocs.infirmary.application.service.student.health.profile;


import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileResponse;

public interface StudentHealthProfileService {

    /**
     * finds a student health profile by id
     * @param id is the unique identifier of the student
     * */
    StudentHealthProfileResponse getStudentHealthProfileById(Long id);


}