package com.rocs.infirmary.application.service.user;

import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.registration.Registration;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.user.User;
/**
 * {@code UserService} is an interface of the UserService
 * */
public interface UserService {
    /**
     * find the user by their username
     *
     * @param username is the username provided by the user
     * @return User
     * */
    User findUserByUsername(String username);
    /**
     * find the user by their email
     *
     * @param email is the email provided by the user
     * @return User
     * */
    Person findUserByRegistrationEmail(String email);
    /**
     * registers the user using their credentials
     *
     * @param registration is the object of the registration that contains the registration credential
     * @return User
     * */
    Registration registerUser(Registration registration);
}
