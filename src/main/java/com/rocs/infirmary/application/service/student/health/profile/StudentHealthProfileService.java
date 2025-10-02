package com.rocs.infirmary.application.service.student.health.profile;


import com.rocs.infirmary.application.dto.student.health.profile.StudentHealthProfileDTO;

public interface StudentHealthProfileService {

    /**
     * finds a student by id
     * @param id is the unique identifier of the student
     * @return User
     * */
    StudentHealthProfileDTO getStudentHealthProfileById(Long id);


}