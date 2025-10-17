package com.rocs.infirmary.application.service.email;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

import static com.rocs.infirmary.application.domain.email.constant.EmailConstant.*;


/**
 * This Service sends emails to user when they update their password.
 */
@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    /**
     * Sends an Email with the new password to the provided email address.
     */
    public void sendNewPasswordEmail(String email,String firstName, String password) throws MessagingException {
        Message message = createEmail(email,firstName, password);
        try (Transport smtpTransport = getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL)) {
            smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
            smtpTransport.sendMessage(message, message.getAllRecipients());
            LOGGER.info("Message sent to email: {}", email);
        }
    }
    /**
     * Sends an Email with the token to the provided email address.
     */
    public void sendNewTokenEmail(String email, String resetUrl) throws MessagingException {
        Message message = createResetPasswordEmail(email, resetUrl);
        try (Transport smtpTransport = getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL)) {
            smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
            smtpTransport.sendMessage(message, message.getAllRecipients());
            LOGGER.info("Message sent to email: {}", email);
        }
    }

    private Message createEmail(String email,String firstName, String password) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("The Lord be with you and good day "+firstName + "\n\nyour account password is: "+ password+"\n\n Infirmary Support team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
    private Message createResetPasswordEmail(String email, String resetUrl) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("The Lord be with you and good day " + "\n\nclick the link below to reset your password \n\n"+ resetUrl+"\n\n Infirmary Support team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }
}