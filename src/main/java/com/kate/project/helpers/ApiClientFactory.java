package com.kate.project.helpers;

import com.kate.project.api.client.BuzzApiClient;
import lombok.Getter;

public class ApiClientFactory {
    @Getter
    private String sessionCookie;
    private static final String BASE_URI = Config.get("BASE_URL");


    public BuzzApiClient getBuzzNewPostApiClient() {
        return new BuzzApiClient(BASE_URI, generateSessionCookie());
    }

    private String generateSessionCookie() {
        return new CookieHelper(BASE_URI).getCookieAfterLogin();
    }
}