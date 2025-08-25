package com.rocs.infirmary.application.listener.authentication.failure;

import com.rocs.infirmary.application.service.login.attempts.LoginAttemptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
/**
 * {@code AuthenticationFailureListener} is a component that handles authentication failure events caused by bad credentials.
 */
@Component
public class AuthenticationFailureListener {

    private LoginAttemptsService loginAttemptsService;
    /**
     * initialize the Login Attempts Service
     *
     * @param loginAttemptsService service responsible for handling login attempts
     */
    @Autowired
    public AuthenticationFailureListener(LoginAttemptsService loginAttemptsService) {
        this.loginAttemptsService = loginAttemptsService;
    }
    /**
     * Listens for authentication failures caused by bad credentials and adds the user's
     *
     * @param authenticationFailureBadCredentialsEvent event triggered on authentication failure
     */
    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent){
        Object principal = authenticationFailureBadCredentialsEvent.getAuthentication().getPrincipal();

        if(principal instanceof String){
            String username = (String) authenticationFailureBadCredentialsEvent.getAuthentication().getPrincipal();
            loginAttemptsService.addUserToLoginAttemptCache(username);
        }
    }
}
