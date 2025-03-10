package com.kate.project.helpers;

import com.kate.project.api.client.LoginApiClient;
import com.kate.project.api.client.BuzzApiClient;
import com.kate.project.api.client.ValidateAuthApiClient;
import lombok.Getter;

public class ApiClientFactory {
    @Getter
    private String sessionCookie;
    private static final String BASE_URI = Config.get("BASE_URL");


    public BuzzApiClient getBuzzNewPostApiClient() {
        return new BuzzApiClient(BASE_URI, generateSessionCookie());
    }

    private String generateSessionCookie() {
        String loginToken = new LoginApiClient(BASE_URI).getTokenFromLoginPage();
        return new ValidateAuthApiClient(BASE_URI, loginToken).getSessionCookie();
    }
}