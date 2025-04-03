package com.kate.project.factory;

import com.kate.project.api.clients.BuzzApiClient;
import com.kate.project.api.clients.UserAdminApiClient;
import com.kate.project.web.entities.User;
import lombok.Getter;

@Getter
public class ApiClientFactory {

    private final BuzzApiClient buzzApiClient;
    private final UserAdminApiClient userAdminApiClient;

    public ApiClientFactory() {
        this(null); // default admin
    }

    public ApiClientFactory(User user) {
        this.buzzApiClient = new BuzzApiClient(user);
        this.userAdminApiClient = new UserAdminApiClient(user);
    }
}