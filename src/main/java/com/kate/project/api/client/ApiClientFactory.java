package com.kate.project.api.client;

import com.kate.project.api.ApiTestConfig;
import com.kate.project.utils.PlaywrightUtils;

public class ApiClientFactory {
    private final PlaywrightUtils playwrightUtils;

    public ApiClientFactory(PlaywrightUtils playwrightUtils) {
        this.playwrightUtils = playwrightUtils;
    }

    public BuzzNewPostApiClient getBuzzNewPostApiClient() {
        return new BuzzNewPostApiClient(
                ApiTestConfig.getBaseUri(),
                ApiTestConfig.getSessionCookie(),
                playwrightUtils::closePage
        );
    }
}