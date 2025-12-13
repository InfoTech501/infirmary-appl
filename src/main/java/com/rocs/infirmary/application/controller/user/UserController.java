package com.rocs.infirmary.application.controller.user;

import com.rocs.infirmary.application.domain.registration.Registration;
import com.rocs.infirmary.application.domain.user.User;
import com.rocs.infirmary.application.domain.user.authenticated.AuthenticatedUser;
import com.rocs.infirmary.application.domain.user.principal.UserPrincipal;
import com.rocs.infirmary.application.exception.domain.*;
import com.rocs.infirmary.application.service.user.UserService;
import com.rocs.infirmary.application.utils.security.jwt.token.provider.JwtTokenProvider;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.rocs.infirmary.application.utils.security.constants.SecurityConstant.JWT_TOKEN_HEADER;

/**
 * The {@code UserController} class use to implement the registration and login functionality of Infirmary web application
 * */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructs a new {@code UserController} with the required dependencies.
     *
     * This constructor is annotated with {@code Autowired} allows
     * Spring to inject the necessary beans at runtime.
     *
     * @param userService the service layer for managing user operations
     * @param authenticationManager the Spring Security authentication manager used to authenticate user credentials
     * @param jwtTokenProvider the provider utility for generating and validating JWT used in secure authentication
     */
    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * {@code login} used to handle the login request, this authenticates a user login based on the provided credential
     * @param user is the object containing the user's credential from the { @code RequestBody}.
     *
     * @return ResponseEntity containing the message, JWT Header and the Http Status
     * */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        authUserLogin(user.getUsername(), user.getPassword());
        User loginUser = this.userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = provideJwtHeader(userPrincipal);
        return new ResponseEntity<>("login success",jwtHeader, HttpStatus.OK);
    }
    /**
     * {@code register} used to handle the registration request, this accepts the object
     * @param registration that contains the credential provided by the user
     * @return ResponseEntity containing the user object, and  Http Status
     * */
    @PostMapping("/register")
    public ResponseEntity<Registration> register(@RequestBody Registration registration) throws UserNotFoundException, EmailExistException, UsernameExistException {
        if(!isValidRegistrationCredentials(registration)){
            throw new InvalidCredentialException("Registration Credential is empty");
        }
        Registration registeredUser = this.userService.registerUser(registration);
        return new ResponseEntity<>(registeredUser,HttpStatus.OK);
    }
    /**
     * {@code resetPassword} used to handle the reset password request, this accepts the object
     * @param user that contains the credential provided by the user
     * @return ResponseEntity containing the user object, and  Http Status
     * */
    @PostMapping("/reset-password")
    public ResponseEntity<User> resetPassword(@RequestParam String token, @RequestBody User user) throws InvalidTokenException {
        try {
            return new ResponseEntity<>(this.userService.resetPassword(token, user),HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * {@code forgetPassword} used to handle the forget password request, this accepts the object
     * @param user that contains the credential provided by the user
     * @return ResponseEntity containing the user object, and  Http Status
     * */
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody User user) throws MessagingException {
        this.userService.forgetPassword(user);
        return new ResponseEntity<>("Email sent successfully",HttpStatus.OK);
    }
    /**
     * {@code getCurrentUser} used to get the authenticated user
     * @param authentication contains the authentication token
     * @return ResponseEntity containing the user object, and  Http Status
     * */
    @GetMapping("/current-user")
    public ResponseEntity<AuthenticatedUser> getCurrentUser(Authentication authentication) {
        return new ResponseEntity<>(this.userService.getAuthenticatedUserDetails(authentication),HttpStatus.OK);
    }
    private void authUserLogin(String username, String password){
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }
    private HttpHeaders provideJwtHeader(UserPrincipal userPrincipal){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWT_TOKEN_HEADER,this.jwtTokenProvider.generateJwtToken(userPrincipal));
       return httpHeaders;
    }
    private Boolean isValidRegistrationCredentials(Registration registration){
        if(registration.getStudent() != null){
            Long lrn = registration.getStudent().getLrn();
            String studentEmail = registration.getStudent().getPerson().getEmail();
            String studentFirstName = registration.getStudent().getPerson().getFirstName();
            String studentMiddleName = registration.getStudent().getPerson().getMiddleName();
            String studentLastName = registration.getStudent().getPerson().getLastName();
            String studentUsername = registration.getStudent().getUser().getUsername();
            String studentPassword = registration.getStudent().getUser().getPassword();
            String gender = registration.getStudent().getPerson().getGender();
            if(lrn == null || Stream.of(studentEmail,studentFirstName,studentMiddleName,studentLastName,studentUsername,studentPassword,gender)
                    .anyMatch(input -> input == null || input.isBlank())){
             throw new InvalidCredentialException("Please provide all required fields");
            }
            if(!isValidEmail(studentEmail)){
                throw new InvalidCredentialException("invalid email address format");
            }
        }
        else if(registration.getEmployee() != null){
            String employeeEmail = registration.getEmployee().getPerson().getEmail();
            String employeeFirstName = registration.getEmployee().getPerson().getFirstName();
            String employeeMiddleName = registration.getEmployee().getPerson().getMiddleName();
            String employeeLastName = registration.getEmployee().getPerson().getLastName();
            String employeeUsername = registration.getEmployee().getUser().getUsername();
            String employeePassword = registration.getEmployee().getUser().getPassword();
            String employmentStatus = registration.getEmployee().getEmploymentStatus();
            int employeeNumber = registration.getEmployee().getEmployeeNumber();
            Date dateEmployeed = registration.getEmployee().getDateEmployed();
            if(employeeNumber <= 0 || Stream.of(employeeEmail,employeeFirstName,employeeMiddleName,employeeLastName,employeeUsername,employeePassword,employmentStatus,dateEmployeed.toString()).anyMatch(String::isBlank) ){
                throw new InvalidCredentialException("Please provide all required field");
            }
            if(!isValidEmail(employeeEmail)){
                throw new InvalidCredentialException("invalid email address format");
            }
        }
        return true;
    }
    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher emailmatcher = pattern.matcher(email);
        return emailmatcher.find();
    }
}
