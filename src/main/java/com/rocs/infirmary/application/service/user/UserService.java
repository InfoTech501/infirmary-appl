package com.rocs.infirmary.application.service.user;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.registration.Registration;
import com.rocs.infirmary.application.domain.user.User;
import com.rocs.infirmary.application.domain.user.authenticated.AuthenticatedUser;
import com.rocs.infirmary.application.exception.domain.InvalidTokenException;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;

import java.util.concurrent.ExecutionException;

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
    /**
     * this is used to set a new password when a user forgets it
     *
     * @param user is the object that contains the user credentials
     * */
    User resetPassword(String token,User user) throws ExecutionException, InvalidTokenException;
    /**
     * this is used to send a reset password request
     *
     * @param user is the object that contains the user credentials
     * */
    void forgetPassword(User user) throws MessagingException;
    /**
     * this is used to get the authenticated user
     *
     * @param authentication object representing the currently authenticated user
     * */
    AuthenticatedUser getAuthenticatedUserDetails(Authentication authentication);
}
