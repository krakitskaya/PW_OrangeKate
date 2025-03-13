package com.kate.project.factory;

import com.kate.project.api.client.BuzzApiClient;
import com.kate.project.api.client.UserAdminApiClient;
import com.kate.project.helpers.Config;
import com.kate.project.helpers.CookieHelper;
import lombok.Getter;

@Getter
public class ApiClientFactory {

    private static final String BASE_URI = Config.get("BASE_URL") + "/api/v2";
    private final BuzzApiClient buzzApiClient;
    private final UserAdminApiClient userAdminApiClient;

    public ApiClientFactory() {
        this.buzzApiClient = new BuzzApiClient(generateSessionCookie());
        this.userAdminApiClient = new UserAdminApiClient(generateSessionCookie());
    }

    public BuzzApiClient getBuzzNewPostApiClient() {
        return buzzApiClient;
    }

    public UserAdminApiClient getUserApiClient() {
        return userAdminApiClient;
    }

    private String generateSessionCookie() {
        return CookieHelper.getCookieAfterLogin();
    }
}