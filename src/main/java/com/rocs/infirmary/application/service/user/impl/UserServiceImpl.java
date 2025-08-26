package com.rocs.infirmary.application.service.user.impl;

import com.rocs.infirmary.application.domain.user.User;
import com.rocs.infirmary.application.domain.user.principal.UserPrincipal;
import com.rocs.infirmary.application.exception.domain.EmailExistException;
import com.rocs.infirmary.application.exception.domain.UserNotFoundException;
import com.rocs.infirmary.application.exception.domain.UsernameExistException;
import com.rocs.infirmary.application.repository.user.UserRepository;
import com.rocs.infirmary.application.service.login.attempts.LoginAttemptsService;
import com.rocs.infirmary.application.service.user.UserService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

import static com.rocs.infirmary.application.exception.constants.ExceptionConstants.USER_NOT_FOUND;
import static com.rocs.infirmary.application.utils.security.enumeration.Role.*;

@Service
@Transactional
@Qualifier(value = "userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private LoginAttemptsService loginAttemptsService;
   @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,LoginAttemptsService loginAttemptsService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginAttemptsService = loginAttemptsService;
    }

    @Override
    public User findUserByUsername(String username) {
      return this.userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByPersonEmail(String email) {
        return this.userRepository.findUserByPersonEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findUserByUsername(username);
        if(user == null){
            LOGGER.info(USER_NOT_FOUND);
            throw new UserNotFoundException(USER_NOT_FOUND);
        }else{
            validateLoginAttempt(user);
            user.setLastLoginDate(new Date());
            this.userRepository.save(user);
            return new UserPrincipal(user);
        }
    }

    @Override
    public User registerUser(User user) {
       validateUsernameEmail(StringUtils.EMPTY, user.getUsername(), user.getPerson().getEmail());
       User newUser = new User();
       String password;
       newUser.setUserId(generateUserId());
       if(user.getPassword() == null){
           password = generatePassword();
       }else{
           password = user.getPassword();
       }
       String encryptedPassword = encodePassword(password);

       newUser.setPerson(user.getPerson());
       newUser.setJoinDate(new Date());
       newUser.setUsername(user.getUsername());
       newUser.setPassword(encryptedPassword);

       newUser.setActive(true);
       newUser.setLocked(false);

       if(user.getRole().equals("teacher")){
           newUser.setRole(TEACHER_ROLE.name());
           newUser.setAuthorities(Arrays.stream(TEACHER_ROLE.getAuthorities()).toList());
       }else if(user.getRole().equals("admin")){
           newUser.setRole(ADMIN_ROLE.name());
           newUser.setAuthorities(Arrays.stream(ADMIN_ROLE.getAuthorities()).toList());
       }else{
           newUser.setRole(USER_ROLE.name());
           newUser.setAuthorities(Arrays.stream(USER_ROLE.getAuthorities()).toList());
       }

        this.userRepository.save(newUser);

       return newUser;
    }

    private User validateUsernameEmail(String currentUsername, String newUsername, String email) throws UserNotFoundException,EmailExistException,UsernameExistException{
       User userBynewUsername = findUserByUsername(newUsername);
       User userByEmail = findUserByPersonEmail(email);

       if(StringUtils.isNotBlank(currentUsername)){
           User currentUser = findUserByUsername(currentUsername);
           if(currentUser == null){
               throw new UserNotFoundException("User not found");
           }
           if(userByEmail != null && !userByEmail.getId().equals(currentUser.getId())){
               throw new EmailExistException("Email is already exist");
           }
           if(userBynewUsername != null && userBynewUsername.getId().equals(currentUser.getId())){
               throw new UsernameExistException("Username is already Exist");
           }
           return currentUser;
       }else{
           if(userBynewUsername != null){
               throw new UsernameExistException("Username is already Exist");
           }
           if(userByEmail != null){
               throw new EmailExistException("Email is already exist");
           }
           return null;
       }

    }
    private String generateUserId(){
       return RandomStringUtils.randomNumeric(10);
    }
    private String generatePassword(){
        return RandomStringUtils.randomAlphanumeric(10);
    }
    private String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }
    private void validateLoginAttempt(User user){
       if(!user.isLocked()){
           if(loginAttemptsService.hasExceedMaxAttempts(user.getUsername())){
               user.setLocked(true);
           }else{
               user.setLocked(false);
           }
       }else{
           loginAttemptsService.evictUserToLoginAttemptCache(user.getUsername());
       }
    }
}
