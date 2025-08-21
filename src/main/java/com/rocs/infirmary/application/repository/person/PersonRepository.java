package com.rocs.infirmary.application.repository.person;

import com.rocs.infirmary.application.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
