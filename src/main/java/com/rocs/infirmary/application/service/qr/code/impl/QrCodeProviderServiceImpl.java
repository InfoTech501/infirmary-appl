package com.rocs.infirmary.application.service.qr.code.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rocs.infirmary.application.domain.student.Student;
import com.rocs.infirmary.application.domain.user.User;
import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import com.rocs.infirmary.application.exception.domain.UserNotFoundException;
import com.rocs.infirmary.application.repository.student.StudentRepository;
import com.rocs.infirmary.application.repository.user.UserRepository;
import com.rocs.infirmary.application.service.qr.code.QrCodeProviderService;
import com.rocs.infirmary.application.utils.security.jwt.token.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
/**
 * Service implementation responsible for generating QR codes that allow parents to view a student's health profile.
 * */
@Service
public class QrCodeProviderServiceImpl implements QrCodeProviderService {
    @Value("${spring.application.base-url}")
    private String baseUrl;
    @Value("${spring.application.endpoints.parent-endpoint}")
    private String parentEndpoint;

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public QrCodeProviderServiceImpl(UserRepository userRepository, StudentRepository studentRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public BufferedImage generateQrCode(Authentication authentication) throws StudentNotFoundException {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new UserNotFoundException("Unauthenticated user");
        }

        User user = this.userRepository.findUserByUsername(authentication.getName());
        if(user == null) {
            throw new UserNotFoundException("User not found");
        }

        Student student = this.studentRepository.findStudentByUserId(user.getId());
        if(student == null){
            throw new StudentNotFoundException("Student not found");
        }

        String parentToken = this.jwtTokenProvider.generateParentJwtToken(student.getLrn());

        String url = baseUrl+parentEndpoint+parentToken;

        int width = 300;
        int height = 300;
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        try {
            BitMatrix matrix = barcodeWriter.encode(url,BarcodeFormat.QR_CODE,width,height);
            return MatrixToImageWriter.toBufferedImage(matrix);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
