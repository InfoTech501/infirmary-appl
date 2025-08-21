package com.rocs.infirmary.application.controller.user;

import com.rocs.infirmary.application.domain.user.User;
import com.rocs.infirmary.application.domain.user.principal.UserPrincipal;
import com.rocs.infirmary.application.service.user.UserService;
import com.rocs.infirmary.application.security.jwt.token.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.rocs.infirmary.application.security.utils.constants.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login-user")
    public ResponseEntity<String> userLogin(@RequestBody User user){
        authUserLogin(user.getUsername(), user.getPassword());
        User loginUser = this.userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = provideJwtHeader(userPrincipal);
        return new ResponseEntity<>("Login success",jwtHeader, HttpStatus.OK);
    }
    @PostMapping("/register-user")
    public ResponseEntity<User> userRegistration(@RequestBody User user){
        User newUser = this.userService.registerUser(user);
        return new ResponseEntity<>(newUser,HttpStatus.OK);
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
