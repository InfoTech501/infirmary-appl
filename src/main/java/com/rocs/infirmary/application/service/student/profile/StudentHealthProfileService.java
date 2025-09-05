package com.rocs.infirmary.application.service.student.profile;

import com.rocs.infirmary.application.domain.person.student.Student;
/**
 * {@code StudentHealthProfileService} is an interface of the Student Health Profile
 * */
public interface StudentHealthProfileService {
     /**
      * Saves a new health profile for a student.
      *
      * @param student the entity containing student information and health related details to be saved
      * @return Student the saved student with all details stored
      * */
     Student addStudentHealthProfile(Student student);
}
