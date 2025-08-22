package com.rocs.infirmary.application.security.utils.constants.authority;

public class Authorities {
    public static String[] USER_AUTHORITIES = {"user:read"};
    public static String[] TEACHER_AUTHORITIES = {"user:read","user:update"};
    public static String[] ADMIN_AUTHORITIES = {"user:read","user:create","user:update","user:delete"};
}
