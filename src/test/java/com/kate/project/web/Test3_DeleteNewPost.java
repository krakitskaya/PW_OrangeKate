package com.kate.project.web;

import com.kate.project.api.ApiTestConfig;
import com.kate.project.api.client.ApiClientFactory;
import com.kate.project.utils.PlaywrightUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class Test3_DeleteNewPost extends BasePlaywrightTest {
    private static final String postText = UUID.randomUUID().toString();
    private ApiClientFactory apiClientFactory;

    @BeforeEach
    void setupApiClientFactory() {
        ApiTestConfig.initializePlaywrightAndLogin();
        PlaywrightUtils playwrightUtils = new PlaywrightUtils("chromium", false); // Adjust headless if needed
        apiClientFactory = new ApiClientFactory(playwrightUtils);
    }

    @Test
    void deleteNewPost() {
        apiClientFactory.getBuzzNewPostApiClient().createNewPost(postText, "text");

        boolean isPostPresent = getLoginHelper().loginToBuzz()
                .deletePost(postText)
                .isBuzzPostTilePresent(postText);

        assertFalse(isPostPresent, "Post deleted successfully");
    }
}