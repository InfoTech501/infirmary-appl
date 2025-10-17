package com.rocs.infirmary.application.repository.user;

import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * {@code PersonRepository} is an interface of User repository
 * */
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * Finds a user by their username.
     *
     * @param username username of the user
     * @return username
     */
    User findUserByUsername(String username);
    /**
     * Finds a user by their email.
     *
     * @param email email of the user
     * @return username
     */
    User findUserByPersonEmail(String email);
}
