package com.kate.project.web.entities;

import com.kate.project.api.client.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private Integer userId;
    private UserRole userRole;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}