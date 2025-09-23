package com.rocs.infirmary.application.repository.department;

import com.rocs.infirmary.application.domain.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * {@code DepartmentRepository} is an interface of department repository
 * */
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
