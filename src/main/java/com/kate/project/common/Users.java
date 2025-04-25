package com.kate.project.common;
import com.kate.project.api.enums.UserRole;
import com.kate.project.web.entities.User;

public class Users {
    public static final User ADMIN_USER = new User(
            Config.get("adminUsername"),
            Config.get("adminPassword"),
            UserRole.ADMIN
    );
}