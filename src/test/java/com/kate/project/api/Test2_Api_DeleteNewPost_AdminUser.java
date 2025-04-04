package com.kate.project.api;

import com.kate.project.helpers.BuzzHelper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class Test2_Api_DeleteNewPost_AdminUser extends BaseApiTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void deleteNewPostApi() {
        int postId = BuzzHelper.createPostApi(postText);

        Response response = defaultApiClientFactory
                .getBuzzApiClient()
                .getDeletePostClient(postId)
                .send();

        verifySuccess(response);
    }
}
