package com.rocs.infirmary.application.service.student.health.information;

import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.information.StudentHealthInformation;
import com.rocs.infirmary.application.exception.domain.SectionNotFoundException;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
/**
 * {@code StudentHealthInformationService} is an interface of the StudentHealthInformationService
 */
public interface StudentHealthInformationService {
    /**
     * Updates the health information of a student.
     *
     * @param studentHealthInformation the student's health information to be updated
     * @return the updated Student entity
     * @throws StudentNotFoundException if the student cannot be found
     * @throws SectionNotFoundException if the specified section does not exist
     */
    Student updateStudentHealthInformation(StudentHealthInformation studentHealthInformation) throws StudentNotFoundException, SectionNotFoundException;
}
