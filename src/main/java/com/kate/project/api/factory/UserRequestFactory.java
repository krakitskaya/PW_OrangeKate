package com.kate.project.api.factory;

import com.kate.project.api.dto.UserRequestDto;
import com.kate.project.api.enums.UserRole;
import com.kate.project.common.Config;
import com.kate.project.web.entities.User;
import org.apache.commons.lang3.RandomStringUtils;

public class UserRequestFactory {

    private static final Integer HARDCODED_EMPLOYEE_NUMBER = 7;

    public static UserRequestDto createUserRequestDtoFromUser(User user) {
        UserRequestDto dto = new UserRequestDto();

        dto.setUsername(
                user.getUsername() != null ? user.getUsername() : RandomStringUtils.randomAlphanumeric(8)
        );

        dto.setStatus(true);
        dto.setEmpNumber(HARDCODED_EMPLOYEE_NUMBER);

        UserRole role = user.getUserRole();
        dto.setUserRoleId(user.getUserRole().getRoleId());
        String password = role == UserRole.ADMIN
                ? Config.get("adminPassword")
                : Config.get("defaultPassword");

        dto.setPassword(password);
        return dto;
    }
}