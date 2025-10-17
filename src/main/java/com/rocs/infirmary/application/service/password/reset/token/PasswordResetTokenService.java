package com.rocs.infirmary.application.service.password.reset.token;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.rocs.infirmary.application.exception.domain.InvalidTokenException;
import com.rocs.infirmary.application.service.login.attempts.LoginAttemptsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
/**
 * {@code PasswordResetTokenService} handles the password reset token and email request attempt service
 * */
@Component
public class PasswordResetTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetTokenService.class);

    private final LoadingCache<String, String> tokenCache;
    private final LoadingCache<String, Integer> requestAttemptCache;
    private static final int MAX_NUMBER_OF_ATTEMPTS = 2;
    private static final int ATTEMPTS_INCREMENT = 1;
    /**
     * initialize the PasswordResetTokenService that holds 100 entries for both reset token and email request attempt and expires within 5 minutes
     * */
    public PasswordResetTokenService() {
        super();
        this.tokenCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return null;
                    }
                });
        this.requestAttemptCache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }
    /**
     * Generates a unique token for the given email and stores it in cache.
     */
    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        tokenCache.put(token, email);
        return token;
    }
    /**
     * this adds the user's email to request attempts and increments the email request attempts of the user
     * @param email email to add to email request attempt cache
     * */
    public void addUserToEmailRequestAttemptCache(String email){
        int requestAttempt = 0;
        try {
            requestAttempt = requestAttemptCache.get(email) + ATTEMPTS_INCREMENT;
        } catch (ExecutionException e) {
            LOGGER.error("Execution Exception occurred{}", e);
        }
        requestAttemptCache.put(email, requestAttempt);
    }
    /**
     * this validates the token used in password reset
     *
     * @param token is the token used to validate the password reset
     * */
    public String validateToken(String token) throws InvalidTokenException {
        String email = tokenCache.getIfPresent(token);
        if (email == null) {
            throw new InvalidTokenException("token is already expired");
        }
        return email;
    }
    /**
     * this removed the token in cache
     *
     * @param token is the token that will be removed from the cache
     * */
    public void evictTokenToCache(String token){
        tokenCache.invalidate(token);
    }
    /**
     * this removed the user's email in cache
     *
     * @param email is the email that will be removed from the cache
     * */
    public void evictEmailToCache(String email){
        requestAttemptCache.invalidate(email);
    }
    /**
     * this checks if the request attempt has exceeds the max email request attempt
     *
     * @param email is the email that will be checked if the user exceed the max email request attempt
     * */
    public boolean hasExceedMaxAttempts(String email){
        try {
            return requestAttemptCache.get(email) >= MAX_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            LOGGER.error("Execution Exception occurred{}", e);
        }
        return false;
    }
}
