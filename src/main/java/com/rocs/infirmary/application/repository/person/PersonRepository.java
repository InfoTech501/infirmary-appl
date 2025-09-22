package com.rocs.infirmary.application.repository.person;

import com.rocs.infirmary.application.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * {@code PersonRepository} is an interface of Person repository
 * */
public interface PersonRepository extends JpaRepository<Person,Long> {
    /**
     * this is used to find the person by its email
     * */
    Person findByEmail(String email);
}
