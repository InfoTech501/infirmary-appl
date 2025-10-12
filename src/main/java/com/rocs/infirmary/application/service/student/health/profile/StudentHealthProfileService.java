package com.rocs.infirmary.application.service.student.health.profile;


import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileResponse;

public interface StudentHealthProfileService {

    /**
     * finds a student health profile by id
     * @param lrn is the unique identifier of the student
     * */
    StudentHealthProfileResponse getStudentHealthProfileByLrn(Long lrn);


}