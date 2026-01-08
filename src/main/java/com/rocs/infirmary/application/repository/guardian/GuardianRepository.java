package com.rocs.infirmary.application.repository.guardian;

import com.rocs.infirmary.application.domain.guardian.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * {@code EmployeeRepository} is an interface of Guardian Repository
 * */
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
}
