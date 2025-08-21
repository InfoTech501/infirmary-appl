package com.rocs.infirmary.application.repository.user;

import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
    User findUserByPersonEmail(String email);
}
