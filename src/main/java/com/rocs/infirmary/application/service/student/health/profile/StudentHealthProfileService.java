package com.rocs.infirmary.application.service.student.health.profile;


import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.exception.domain.InvalidCredentialException;
import com.rocs.infirmary.application.exception.domain.StudentHealthProfileNotFoundException;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;

/**
 * {@code StudentHealthProfileService} is an Interface of StudentHealthProfileServiceImpl
 * */
public interface StudentHealthProfileService {

    /**
     * finds a student health profile by id
     * @param lrn is the unique identifier of the student
     * */
    StudentHealthProfileResponse getStudentHealthProfileByLrn(Long lrn)throws InvalidCredentialException, StudentHealthProfileNotFoundException;

    /**
     * Saves a new health profile for a student.
     *
     * @param student the entity containing student information and health related details to be saved
     * @return Student the saved student with all details stored
     * */
    Student addStudentHealthProfile(Student student) throws StudentNotFoundException;
}