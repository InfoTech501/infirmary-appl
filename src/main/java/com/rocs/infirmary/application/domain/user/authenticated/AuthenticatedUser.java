package com.rocs.infirmary.application.domain.user.authenticated;

import com.rocs.infirmary.application.domain.department.Department;
import com.rocs.infirmary.application.domain.employee.Employee;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
/**
 * this contains the credentials of authenticated user
 * */
@Data
public class AuthenticatedUser {
    @Id
    private Student student;
    private Employee employee;
}
