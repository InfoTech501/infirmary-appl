package com.rocs.infirmary.application.utils.security.enumeration;

import static com.rocs.infirmary.application.utils.security.constants.authority.Authorities.*;
/**
 * {@code Role } is an Enum representing the different roles in the system and their associated authorities.
 */
public enum Role {
    /**
     * role for student user with read access
     * */
    STUDENT_ROLE(STUDENT_AUTHORITIES),
    /**
     * role for teacher with create,read and update access
     * */
    TEACHER_ROLE(TEACHER_AUTHORITIES),
    /**
     * role for admin with create,read, update, and delete access
     * */
    ADMIN_ROLE(ADMIN_AUTHORITIES);

    private final String[] authorities;

    /**
     * Constructor for the Role enum.
     *
     * @param authorities authorities associated with the role
     */
    Role(String... authorities) {
        this.authorities = authorities;
    }
    /**
     * Get the authorities associated with the role.
     *
     * @return array of authorities
     */
    public String[] getAuthorities(){
        return authorities;
    }
}
