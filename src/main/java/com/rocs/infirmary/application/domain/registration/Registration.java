package com.rocs.infirmary.application.domain.registration;

import com.rocs.infirmary.application.domain.employee.Employee;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.user.User;
import lombok.Data;
/**
 * this is used as the data transfer object for user registration
 * */
@Data
public class Registration {
    private Student student;
    private Employee employee;
}
