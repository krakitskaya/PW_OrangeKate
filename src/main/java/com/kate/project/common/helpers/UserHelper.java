package com.kate.project.common.helpers;

import com.kate.project.api.dto.UserRequestDto;
import com.kate.project.api.enums.UserRole;
import com.kate.project.api.factory.ApiClientFactory;
import com.kate.project.api.factory.UserRequestFactory;
import com.kate.project.web.entities.User;

import static com.kate.project.common.Users.ADMIN_USER;

public class UserHelper {
    private static final ApiClientFactory apiClientFactory = new ApiClientFactory();

    private static User createUserViaApi(User user) {
        UserRequestDto requestDto = UserRequestFactory.createUserRequestDtoFromUser(user);
        var response = apiClientFactory
                .getUserAdminApiClient()
                .createUser(requestDto, ADMIN_USER);

        response.assertSuccess();

        var responseDto = response.body();
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
        var response = apiClientFactory
                .getUserAdminApiClient()
                .deleteUser(user.getUserId(), ADMIN_USER);
        response.assertSuccess();
    }
}