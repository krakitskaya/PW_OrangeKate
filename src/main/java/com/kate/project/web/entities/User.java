package com.kate.project.web.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private Integer userId;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}