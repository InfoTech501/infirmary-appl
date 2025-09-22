package com.rocs.infirmary.application.repository.employee;

import com.rocs.infirmary.application.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * {@code EmployeeRepository} is an interface of Employee repository
 * */
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Employee findEmployeeByUserId(Long id);
}
