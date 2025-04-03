package com.kate.project.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class Test1_Api_AddNewPost_AdminUser extends BaseApiTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void addNewPostApi() {
        Response response = defaultApiClientFactory
                .getBuzzApiClient()
                .createPost(postText)
                .send();

        verifySuccess(response);
    }
}

//TODO getallPosts проверить что есть мой пост