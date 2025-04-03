package com.kate.project.api.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN(1),
    ESS(2);

    private final Integer roleId;

    UserRole(Integer roleId) {
        this.roleId = roleId;
    }

    public static UserRole fromRoleId(Integer roleId) {
        for (UserRole role : values()) {
            if (role.roleId.equals(roleId)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid roleId: " + roleId);
    }
}