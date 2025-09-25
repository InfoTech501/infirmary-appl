package com.rocs.infirmary.application.repository.guardian;

import com.rocs.infirmary.application.domain.guardian.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian,Long> {
}
