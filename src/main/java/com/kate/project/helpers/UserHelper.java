package com.kate.project.helpers;

import com.kate.project.api.dto.CreatedUserDto;
import com.kate.project.api.dto.UserRequestDto;
import com.kate.project.api.enums.UserRole;
import com.kate.project.factory.ApiClientFactory;
import com.kate.project.factory.UserRequestFactory;
import com.kate.project.web.entities.User;

public class UserHelper {
    private static final ApiClientFactory apiClientFactory = new ApiClientFactory();

    private static User createUserViaApi(User user) {
        UserRequestDto requestDto = UserRequestFactory.createUserRequestDtoFromUser(user);
        CreatedUserDto responseDto = apiClientFactory
                .getUserAdminApiClient()
                .getCreateUserClient(requestDto)
                .sendAndExtractUser();

        return new User(
                requestDto.getUsername(),
                requestDto.getPassword(),
                responseDto.getId(),
                UserRole.fromRoleId(responseDto.getUserRole().getId())
        );
    }

    public static User createAdminUserViaApi() {
        return createUserViaApi(new User(true));
    }

    public static User createEssUserViaApi() {
        return createUserViaApi(new User(false));
    }

    public static void deleteUserViaApi(User user) {
        apiClientFactory
                .getUserAdminApiClient()
                .getDeleteUserClient(user.getUserId())
                .sendAndVerifySuccess();
    }
}
