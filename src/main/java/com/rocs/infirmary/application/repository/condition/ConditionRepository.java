package com.rocs.infirmary.application.repository.condition;

import com.rocs.infirmary.application.domain.condition.Condition;
import com.rocs.infirmary.application.domain.guardian.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConditionRepository extends JpaRepository<Condition,Long> {
}
