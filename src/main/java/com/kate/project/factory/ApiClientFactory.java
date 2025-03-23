package com.kate.project.factory;

import com.kate.project.api.client.BuzzApiClient;
import com.kate.project.api.client.UserAdminApiClient;
import com.kate.project.helpers.CookieHelper;
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
        String sessionCookie = generateSessionCookie(user);
        this.buzzApiClient = new BuzzApiClient(sessionCookie);
        this.userAdminApiClient = new UserAdminApiClient(sessionCookie);
    }

    private String generateSessionCookie(User user) {
        if (user == null) {
            return CookieHelper.getCookieAfterLogin(); // default admin
        }
        return CookieHelper.getCookieAfterLogin(user);
    }
}