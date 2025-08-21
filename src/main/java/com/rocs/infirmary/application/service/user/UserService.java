package com.rocs.infirmary.application.service.user;

import com.rocs.infirmary.application.domain.user.User;

public interface UserService {
    User findUserByUsername(String username);
    User findUserByPersonEmail(String email);
    User registerUser(User user);
}
