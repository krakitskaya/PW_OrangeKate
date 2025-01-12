package com.kate.project.api;

import com.kate.project.api.client.ApiClientFactory;
import com.kate.project.utils.PlaywrightUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test1_Api_CreateNewPost {
    private ApiClientFactory apiClientFactory;

    @BeforeEach
    void setup() {
        ApiTestConfig.initializePlaywrightAndLogin();
        PlaywrightUtils playwrightUtils = new PlaywrightUtils("chromium", true);
        apiClientFactory = new ApiClientFactory(playwrightUtils);
    }

    @Test
    public void testPostEndpoint() {
        apiClientFactory.getBuzzNewPostApiClient().createNewPost("NewPost", "text");
    }
}