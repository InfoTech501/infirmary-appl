package com.rocs.infirmary.application.exception.handling;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.rocs.infirmary.application.domain.http.response.HttpResponse;
import com.rocs.infirmary.application.exception.domain.*;
import jakarta.persistence.NoResultException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static com.rocs.infirmary.application.exception.constants.ExceptionConstants.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandling implements ErrorController {

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException() {
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> accountLockedException(){
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_IS_LOCKED);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<HttpResponse> methodNotAllowedException(){
        return createHttpResponse(BAD_REQUEST,METHOD_IS_NOT_ALLOWED);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(){
        return createHttpResponse(BAD_REQUEST,INTERNAL_SERVER_ERR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialException(){
        return createHttpResponse(BAD_REQUEST,INCORRECT_CREDENTIAL);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(){
        return createHttpResponse(FORBIDDEN,NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception){
        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception){
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> usernameExistException(NoResultException exception){
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MedicineNotFoundException.class)
    public ResponseEntity<HttpResponse> medicineNotFound(MedicineNotFoundException exception){
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException e) {
        return createHttpResponse(BAD_REQUEST, "There is no mapping for this URL");
    }
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<HttpResponse>studentNotFoundException(StudentNotFoundException e){
        return createHttpResponse(BAD_REQUEST,e.getMessage());
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<HttpResponse>invalidTokenException(InvalidTokenException e){
        return createHttpResponse(BAD_REQUEST,e.getMessage());
    }
    /**
     * Handles the {@link SectionNotFoundException} when a section cannot be found.
     * Returns an HTTP response with a bad request status and the exception message.
     *
     * @param e the thrown SectionNotFoundException
     * @return a ResponseEntity containing the HTTP response and error message
     */
    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<HttpResponse>sectionNotFoundException(SectionNotFoundException e){
        return createHttpResponse(BAD_REQUEST,e.getMessage());
    }
    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message){
        return new ResponseEntity<>(new HttpResponse(status.value(), status,
                status.getReasonPhrase().toUpperCase(), message.toUpperCase()), status);
    }
}
