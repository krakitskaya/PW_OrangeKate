package com.kate.project.api;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class Test2_Api_DeleteNewPost extends BaseApiTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void deleteNewPostApi() {
        int postId = apiClientFactory.getBuzzNewPostApiClient().createNewPost(postText, "text");
        apiClientFactory.getBuzzNewPostApiClient().deletePost(postId);
    }
}