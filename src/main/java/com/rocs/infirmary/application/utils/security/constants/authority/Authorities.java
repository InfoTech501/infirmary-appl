package com.rocs.infirmary.application.utils.security.constants.authority;
/**
 * {@code Authorities} provides the predefined authorization of the user
 * */
public class Authorities {
    /**
     * authorities for general users
     * */
    public static String[] USER_AUTHORITIES = {"user:read"};
    /**
     * Authorities for teacher
     * */
    public static String[] TEACHER_AUTHORITIES = {"user:read","user:update"};
    /**
     * Authorities for admin
     * */
    public static String[] ADMIN_AUTHORITIES = {"user:read","user:create","user:update","user:delete"};
}
