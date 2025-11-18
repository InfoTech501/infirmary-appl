package com.rocs.infirmary.application.controller.student;


import com.rocs.infirmary.application.domain.student.clinic.visit.history.ClinicVisitHistory;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.student.health.information.StudentHealthInformation;
import com.rocs.infirmary.application.domain.student.list.StudentResponse;
import com.rocs.infirmary.application.exception.domain.SectionNotFoundException;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.service.student.StudentService;
import com.rocs.infirmary.application.service.qr.code.QrCodeProviderService;

import com.rocs.infirmary.application.service.student.clinic.visit.history.ClinicVisitHistoryService;
import com.rocs.infirmary.application.service.student.health.information.StudentHealthInformationService;
import com.rocs.infirmary.application.domain.student.health.profile.StudentHealthProfileResponse;
import com.rocs.infirmary.application.service.student.health.profile.StudentHealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This controller is used to handle operations related to the Student services
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentHealthInformationService studentHealthInformationService;
    private final ClinicVisitHistoryService clinicVisitHistoryService;
    private final StudentService studentService;
    private final StudentHealthProfileService studentHealthProfileService;
    private final QrCodeProviderService qrCodeProviderService;

    @Autowired
    public StudentController(StudentHealthInformationService studentHealthInformationService, ClinicVisitHistoryService clinicVisitHistoryService, StudentHealthProfileService studentHealthProfileService, StudentService studentService, QrCodeProviderService qrCodeProviderService) {
        this.studentHealthInformationService = studentHealthInformationService;
        this.clinicVisitHistoryService = clinicVisitHistoryService;
        this.studentHealthProfileService = studentHealthProfileService;
        this.studentService = studentService;
        this.qrCodeProviderService = qrCodeProviderService;
    }

  
    /**
     * This converter allows Spring to automatically serialize {@code BufferedImage} responses to HTTP responses in image format.
     * @return a {@link BufferedImageHttpMessageConverter} for handling image responses
     */
    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
    /**
     * this is used to facilitate request for updating student health profile
     *
     * @return ResponseEntity containing the Student clinic visit history, and the Http Status
     * */
    @PutMapping("/health-profile/update")
    public ResponseEntity<Student> updateStudent(@RequestBody StudentHealthInformation student) throws StudentNotFoundException, SectionNotFoundException {
        return new ResponseEntity<>(this.studentHealthInformationService.updateStudentHealthInformation(student),HttpStatus.OK);
    }

    /**
     * this is used to facilitate request for getting the Student Clinic Visit History
     * @param lrn is used as the unique identifier for each student
     *
     * @return ResponseEntity containing the Student clinic visit history, and the Http Status
     * */
    @GetMapping("/clinic/visit")
    public ResponseEntity<List<ClinicVisitHistory>> viewClinicVisitHistory(@RequestParam Long lrn) throws StudentNotFoundException {
        return new ResponseEntity<>(this.clinicVisitHistoryService.findClinicVisitByStudentLrn(lrn), HttpStatus.OK);
    }
    /**
     * this is used to facilitate request for getting all Clinic Visit History for all student
     *
     * @return ResponseEntity containing the Student clinic visit history, and the Http Status
     * */
    @GetMapping("/clinic/visit/view-all")
    public ResponseEntity<List<ClinicVisitHistory>> viewClinicVisitHistory(){
        return new ResponseEntity<>(this.clinicVisitHistoryService.findAllClinicVisits(), HttpStatus.OK);
    }

    /**
     * used to facilitate the request for getting the student health profile of a student based on their lrn
     *
     * @return ResponseEntity containing the Student health profile, and http Status
     * */
    @GetMapping("/health-profile")
    public ResponseEntity<StudentHealthProfileResponse> findStudentHealthProfileByLrn(@RequestParam Long lrn) {
        StudentHealthProfileResponse studentHealthProfile = studentHealthProfileService.getStudentHealthProfileByLrn(lrn);
        return new ResponseEntity<>(studentHealthProfile, HttpStatus.OK);
    }

    /**
     * used to facilitate the request for getting a list of all students in
     *
     * @return ResponseEntity list for all Students, and http Status
     * */
    @GetMapping("/view-all")
    public ResponseEntity<List<StudentResponse>> viewAllStudents(){
        return new ResponseEntity<>(this.studentService.findAllStudents(), HttpStatus.OK);
    }
      
    /**
     * used to facilitate the request for generating the parent qr code for view student health profile
     *
     * @return ResponseEntity containing the generated qr code, and http Status
     * */
    @GetMapping(value = "/generate-qr",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQrCode(Authentication authentication) throws StudentNotFoundException {
        return new ResponseEntity<>(qrCodeProviderService.generateQrCode(authentication),HttpStatus.OK);
    }
    
}
