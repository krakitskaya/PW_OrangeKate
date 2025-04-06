package com.kate.project.web.entities;

import com.kate.project.api.enums.UserRole;
import lombok.Getter;

@Getter
public class User {
    private final String username;
    private final String password;
    private final Integer userId;
    private final UserRole userRole;

    public User(String username, String password, Integer userId, UserRole userRole) {
        if (userRole == null) {
            throw new IllegalArgumentException("UserRole must not be null");
        }
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.userRole = userRole;
    }

    public User(String username, String password, UserRole userRole) {
        this(username, password, null, userRole);
    }

    public User(Boolean isAdmin) {
        this(null, null, null, isAdmin ? UserRole.ADMIN : UserRole.ESS);
    }
}