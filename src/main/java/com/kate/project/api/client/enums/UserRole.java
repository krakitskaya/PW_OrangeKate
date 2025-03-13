package com.kate.project.api.client.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN(1),
    ESS(2);

    private final Integer roleId;

    UserRole(Integer roleId) {
        this.roleId = roleId;
    }
}