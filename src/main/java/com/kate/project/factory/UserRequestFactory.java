package com.kate.project.factory;

import com.kate.project.api.dto.UserRequestDto;
import com.kate.project.api.enums.UserRole;
import com.kate.project.helpers.Config;
import org.apache.commons.lang3.RandomStringUtils;

public class UserRequestFactory {

    private static final Integer HARDCODED_EMPLOYEE_NUMBER = 1;

    public static UserRequestDto createUserRequestDto(boolean isAdmin) {
        UserRequestDto dto = new UserRequestDto();

        dto.setUsername(RandomStringUtils.randomAlphanumeric(8));
        dto.setStatus(true);
        dto.setEmpNumber(HARDCODED_EMPLOYEE_NUMBER);

        if (dto.getPassword() == null) {
            dto.setPassword(isAdmin ? Config.get("adminPassword") : Config.get("defaultPassword"));
        }

        dto.setUserRoleId(isAdmin ? UserRole.ADMIN.getRoleId() : UserRole.ESS.getRoleId());

        return dto;
    }
}