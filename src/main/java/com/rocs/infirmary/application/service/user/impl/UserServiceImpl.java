package com.rocs.infirmary.application.service.user.impl;

import com.rocs.infirmary.application.domain.department.Department;
import com.rocs.infirmary.application.domain.employee.Employee;
import com.rocs.infirmary.application.domain.person.Person;
import com.rocs.infirmary.application.domain.registration.Registration;
import com.rocs.infirmary.application.domain.section.Section;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.user.User;
import com.rocs.infirmary.application.domain.user.principal.UserPrincipal;
import com.rocs.infirmary.application.exception.domain.EmailExistException;
import com.rocs.infirmary.application.exception.domain.UserNotFoundException;
import com.rocs.infirmary.application.exception.domain.UsernameExistException;
import com.rocs.infirmary.application.repository.department.DepartmentRepository;
import com.rocs.infirmary.application.repository.employee.EmployeeRepository;
import com.rocs.infirmary.application.repository.person.PersonRepository;
import com.rocs.infirmary.application.repository.section.SectionRepository;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.repository.user.UserRepository;
import com.rocs.infirmary.application.service.email.EmailService;
import com.rocs.infirmary.application.service.login.attempts.LoginAttemptsService;
import com.rocs.infirmary.application.service.user.UserService;
import com.rocs.infirmary.application.utils.security.enumeration.Role;
import jakarta.mail.MessagingException;
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

import java.util.*;

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
    private StudentRepository studentRepository;
    private PersonRepository personRepository;
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private EmailService emailService;
    /**
     * this creates a constructor for UserServiceImpl that is used to inject the required dependencies
     * */
   @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           LoginAttemptsService loginAttemptsService,
                           StudentRepository studentRepository,
                           PersonRepository personRepository,
                           EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginAttemptsService = loginAttemptsService;
        this.studentRepository = studentRepository;
        this.personRepository = personRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.emailService = emailService;
    }

    @Override
    public User findUserByUsername(String username) {
      return this.userRepository.findUserByUsername(username);
    }

    @Override
    public Person findUserByRegistrationEmail(String email) {
        return this.personRepository.findByEmail(email);
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
    public Registration registerUser(Registration registration) {
       if(registration.getStudent() != null){
           return registerStudent(registration);
       }else if(registration.getEmployee() != null) {
           return registerEmployee(registration);
       }
        return registration;
    }

    private User validateUsername(String currentUsername, String newUsername) throws UserNotFoundException,EmailExistException,UsernameExistException{
       User userEmail = findUserByUsername(newUsername);

       if(StringUtils.isNotBlank(currentUsername)){
           User currentUser = findUserByUsername(currentUsername);
           if(currentUser == null){
               throw new UserNotFoundException("User not found");
           }
           if(userEmail != null && !userEmail.getId().equals(currentUser.getId())){
               throw new EmailExistException("Email is already exist");
           }
           if(userEmail != null && userEmail.getId().equals(currentUser.getId())){
               throw new UsernameExistException("Username is already Exist");
           }
           return currentUser;
       }else{
           if(userEmail != null){
               throw new UsernameExistException("Username is already Exist");
           }
           if(userEmail != null){
               throw new EmailExistException("Email is already exist");
           }
           return null;
       }

    }

    @Override
    public User forgetPassword(User user) throws MessagingException {
        String username = user.getUsername();
        User newUser = this.userRepository.findUserByUsername(username);
        if(newUser == null){
            throw new UserNotFoundException("username does not exist");
        }
        String password = user.getPassword();
        String encryptedPassword = encodePassword(password);
        newUser.setPassword(encryptedPassword);
        newUser.setLocked(false);
        newUser.setActive(true);


        Student studentAccount = this.studentRepository.findStudentByUserId(newUser.getId());
        Employee employeeAccount = this.employeeRepository.findEmployeeByUserId(newUser.getId());

        if(studentAccount != null && studentAccount.getPerson().getEmail() != null) {
            emailService.sendNewPasswordEmail(studentAccount.getPerson().getEmail(),studentAccount.getPerson().getFirstName(),password);
        } else if (employeeAccount != null && employeeAccount.getPerson().getEmail() != null) {
            emailService.sendNewPasswordEmail(employeeAccount.getPerson().getEmail(),employeeAccount.getPerson().getFirstName(),password);
        }
        userRepository.save(newUser);
        return newUser;
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

    private Registration registerStudent(Registration registration){
        validateUsername(StringUtils.EMPTY, registration.getStudent().getUser().getUsername());

        String username = registration.getStudent().getUser().getUsername();
        String password = registration.getStudent().getUser().getPassword() == null
                ? generatePassword()
                : registration.getStudent().getUser().getPassword();

        User newUser = new User();
        newUser.setPerson(registration.getStudent().getPerson());
        newUser.setUserId(generateUserId());
        newUser.setUsername(username);
        newUser.setPassword(encodePassword(password));
        newUser.setActive(true);
        newUser.setLocked(false);
        newUser.setJoinDate(new Date());

        newUser.setRole(STUDENT_ROLE.name());
        newUser.setAuthorities(Arrays.stream(STUDENT_ROLE.getAuthorities()).toList());

        Student student = new Student();
        student.setPerson(registration.getStudent().getPerson());
        student.setSection(registration.getStudent().getSection());
        student.setLrn(registration.getStudent().getLrn());
        student.setUser(newUser);

        Student savedStudent = this.studentRepository.save(student);
        Registration savedRegistration = new Registration();
        savedRegistration.setStudent(savedStudent);
        return savedRegistration;
    }
    private Registration registerEmployee(Registration registration){
        validateUsername(StringUtils.EMPTY, registration.getEmployee().getUser().getUsername());

        String username = registration.getEmployee().getUser().getUsername();
        String password = registration.getEmployee().getUser().getPassword() == null
                ? generatePassword()
                : registration.getEmployee().getUser().getPassword();

        User newUser = new User();
        newUser.setPerson(registration.getEmployee().getPerson());
        newUser.setUserId(generateUserId());
        newUser.setUsername(username);
        newUser.setPassword(encodePassword(password));
        newUser.setActive(true);
        newUser.setLocked(false);
        newUser.setJoinDate(new Date());

        newUser.setRole(TEACHER_ROLE.name());
        newUser.setAuthorities(Arrays.stream(TEACHER_ROLE.getAuthorities()).toList());

        Employee employee = new Employee();
        employee.setPerson(registration.getEmployee().getPerson());

        List<Department> departmentList = this.departmentRepository.findAll();

        String deptName = registration.getEmployee().getDepartment().getDepartmentName();

        Department department = departmentList.stream()
                .filter(d -> d.getDepartmentName().equalsIgnoreCase(deptName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department not found: " + deptName));

        employee.setDepartment(department);
        employee.setUser(newUser);
        employee.setEmployeeNumber(registration.getEmployee().getEmployeeNumber());
        employee.setDateEmployed(registration.getEmployee().getDateEmployed());
        employee.setEmploymentStatus(registration.getEmployee().getEmploymentStatus());

        Employee savedEmployee = this.employeeRepository.save(employee);
        Registration savedRegistration = new Registration();
        savedRegistration.setEmployee(savedEmployee);
        return savedRegistration;
    }
}
