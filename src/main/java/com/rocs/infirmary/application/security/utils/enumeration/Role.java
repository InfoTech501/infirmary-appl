package com.rocs.infirmary.application.security.utils.enumeration;

import static com.rocs.infirmary.application.security.utils.constants.authority.Authorities.TEACHER_AUTHORITIES;
import static com.rocs.infirmary.application.security.utils.constants.authority.Authorities.USER_AUTHORITIES;

public enum Role {
    USER_ROLE(USER_AUTHORITIES),
    TEACHER_ROLE(TEACHER_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }
}
