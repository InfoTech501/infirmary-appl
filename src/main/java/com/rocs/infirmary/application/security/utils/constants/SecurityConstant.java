package com.rocs.infirmary.application.security.utils.constants;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String ROCS = "RoCS";
    public static final String ROCS_ADMINISTRATION = "Infirmary Application self Service";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You cannot access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHODS = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/user/portal/login-user","user/portal/register-user"};
}
