package com.rocs.infirmary.application.security.utils.enumeration;

import static com.rocs.infirmary.application.security.utils.constants.authority.Authorities.*;

public enum Role {
    USER_ROLE(USER_AUTHORITIES),
    TEACHER_ROLE(TEACHER_AUTHORITIES),
    ADMIN_ROLE(ADMIN_AUTHORITIES);

    private final String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }
}
