package com.rocs.infirmary.application.controller.user;

import com.rocs.infirmary.application.domain.registration.Registration;
import com.rocs.infirmary.application.domain.user.User;
import com.rocs.infirmary.application.domain.user.principal.UserPrincipal;
import com.rocs.infirmary.application.service.user.UserService;
import com.rocs.infirmary.application.utils.security.jwt.token.provider.JwtTokenProvider;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.rocs.infirmary.application.utils.security.constants.SecurityConstant.JWT_TOKEN_HEADER;

/**
 * The {@code UserController} class use to implement the registration and login functionality of Infirmary web application
 * */
@RestController
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
        return new ResponseEntity<>("Login success",jwtHeader, HttpStatus.OK);
    }
    /**
     * {@code register} used to handle the registration request, this accepts the object
     * @param registration that contains the credential provided by the user
     * @return ResponseEntity containing the user object, and  Http Status
     * */
    @PostMapping("/register")
    public ResponseEntity<Registration> register(@RequestBody Registration registration){
        Registration registeredUser = this.userService.registerUser(registration);
        return new ResponseEntity<>(registeredUser,HttpStatus.OK);
    }
    /**
     * {@code forgetPassword} used to handle the forget password request, this accepts the object
     * @param user that contains the credential provided by the user
     * @return ResponseEntity containing the user object, and  Http Status
     * */
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody User user) throws MessagingException {
        this.userService.forgetPassword(user);
        return new ResponseEntity<>("Email is sent",HttpStatus.OK);
    }

    private void authUserLogin(String username, String password){
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }
    private HttpHeaders provideJwtHeader(UserPrincipal userPrincipal){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWT_TOKEN_HEADER,this.jwtTokenProvider.generateJwtToken(userPrincipal));
       return httpHeaders;
    }
}
