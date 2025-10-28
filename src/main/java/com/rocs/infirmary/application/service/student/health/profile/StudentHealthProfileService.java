package com.rocs.infirmary.application.service.student.health.profile;


import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;

/**
 * {@code StudentHealthProfileService} is an Interface of StudentHealthProfileServiceImpl
 * */
public interface StudentHealthProfileService {

    /**
     * finds a student health profile by id
     * @param lrn is the unique identifier of the student
     * */
    StudentHealthProfileResponse getStudentHealthProfileByLrn(Long lrn);


}