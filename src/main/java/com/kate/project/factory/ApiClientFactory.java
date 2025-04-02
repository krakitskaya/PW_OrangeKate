package com.kate.project.factory;

import com.kate.project.api.client.BuzzApiClient;
import com.kate.project.api.client.UserAdminApiClient;
import com.kate.project.web.entities.User;
import lombok.Getter;

@Getter
public class ApiClientFactory {

    private final BuzzApiClient buzzApiClient;
    private final UserAdminApiClient userAdminApiClient;

    public ApiClientFactory() {
        this(null); // use default admin
    }

    public ApiClientFactory(User user) {
        this.buzzApiClient = new BuzzApiClient(user);
        this.userAdminApiClient = new UserAdminApiClient(user);
    }
}