package com.kate.project.api.factory;

import com.kate.project.api.clients.BuzzApiClient;
import com.kate.project.api.clients.UserAdminApiClient;
import lombok.Getter;

@Getter
public class ApiClientFactory {

    private final BuzzApiClient buzzApiClient = new BuzzApiClient();
    private final UserAdminApiClient userAdminApiClient = new UserAdminApiClient();
}