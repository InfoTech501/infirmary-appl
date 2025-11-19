package com.rocs.infirmary.application.service.qr.code;

import com.rocs.infirmary.application.exception.domain.StudentNotFoundException;
import org.springframework.security.core.Authentication;

import java.awt.image.BufferedImage;
/**
 * {@code QrCodeProviderService} is an Interface of QrCodeProviderServiceImpl
 * */
public interface QrCodeProviderService {
    /**
     * Generates a QR code image for the authenticated student.
     * The QR code encodes a URL containing a secure and time limited parent JWT token that allows
     * parents to view the student's health profile without requiring login.
     *
     * @param authentication the authentication object of the currently logged-in student
     * @return a {@code BufferedImage} representing the QR code
     */
    BufferedImage generateQrCode(Authentication authentication) throws StudentNotFoundException;
}
