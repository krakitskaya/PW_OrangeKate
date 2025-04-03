package com.kate.project.api.clients;

import com.kate.project.api.clients.userAdmin.CreateUserClient;
import com.kate.project.api.clients.userAdmin.DeleteUserClient;
import com.kate.project.web.entities.User;

public class UserAdminApiClient extends BaseApiClient {
    public UserAdminApiClient(User user) {
        super(user);
    }

    public CreateUserClient getCreateUserClient(Object userRequestDto) {
        return new CreateUserClient(context, userRequestDto);
    }

    public DeleteUserClient getDeleteUserClient(Integer userId) {
        return new DeleteUserClient(context, userId);
    }
}