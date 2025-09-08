package com.rocs.infirmary.application.exception.constants;
/**
 * {@code ExceptionConstants} holds the constant messages for exceptions
 * */
public class ExceptionConstants {
    /**
     * error message that indicates the Account is locked
     * */
    public static final String ACCOUNT_IS_LOCKED = "Your account has been locked";
    /**
     * error message that indicates the Method used is not allowed
     * */
    public static final String METHOD_IS_NOT_ALLOWED = "The request method is not allowed";
    /**
     * error message that indicates there is an internal server erro
     * */
    public static final String INTERNAL_SERVER_ERR = "An error occurred while processing the request";
    /**
     * error message that indicates the User enters an incorrect credentials
     * */
    public static final String INCORRECT_CREDENTIAL = "Username/Password is incorrect";
    /**
     * error message that indicates the Account is disabled
     * */
    public static final String ACCOUNT_DISABLED = "Your account has been disabled";
    /**
     * error message that indicates the user don't have enough permission
     * */
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    /**
     * error message that indicates the there is an error in path
     * */
    public static final String ERROR_PATH = "/error";
    /**
     * error message that indicates the user not found
     * */
    public static final String USER_NOT_FOUND = "User not found";
}
