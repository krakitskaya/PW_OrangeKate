package com.kate.project.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kate.project.api.client.dto.CreatedUserDto;
import com.kate.project.api.client.dto.UserRequestDto;
import com.kate.project.factory.ApiClientFactory;
import com.kate.project.factory.UserRequestFactory;
import com.kate.project.web.entities.User;

public class UserHelper {
    private static final ApiClientFactory apiClientFactory = new ApiClientFactory();

    public static User createAdminUserViaApi() throws JsonProcessingException {
        return createUser(true);
    }

    public static User createEssUserViaApi() throws JsonProcessingException {
        return createUser(false);
    }

    private static User createUser(boolean isAdmin) throws JsonProcessingException {
        UserRequestDto requestDto = UserRequestFactory.createUserRequestDto(isAdmin);
        CreatedUserDto responseDto = apiClientFactory.getUserAdminApiClient().createNewUser(requestDto);
        return new User(requestDto.getUsername(),
                requestDto.getPassword(),
                responseDto.getId(),
                UserRole.fromRoleId(responseDto.getUserRole().getId()));
    }

    public static void deleteUserViaApi(User user) {
        apiClientFactory.getUserAdminApiClient().deleteUserById(user.getUserId());
    }
}