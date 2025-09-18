package com.rocs.infirmary.application.repository.employee;

import com.rocs.infirmary.application.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
}
