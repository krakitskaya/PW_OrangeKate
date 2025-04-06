package com.kate.project.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class Test4_AddNewPost_Negative_NoCookie extends BaseApiTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void addNewPostApi() {
        Response response = defaultApiClientFactory
                .getBuzzApiClient()
                .getCreatePostClient(postText)
                .prepareBuilder()
                .removeCookie()
                .send();

        verifyUnauthorized(response);
    }
}
