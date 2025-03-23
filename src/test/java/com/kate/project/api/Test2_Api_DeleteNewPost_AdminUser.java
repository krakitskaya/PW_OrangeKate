package com.kate.project.api;

import com.kate.project.BasePlaywrightTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class Test2_Api_DeleteNewPost_AdminUser extends BasePlaywrightTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void deleteNewPostApi() {
        int postId = defaultApiClientFactory.getBuzzApiClient().createNewPost(postText);
        defaultApiClientFactory.getBuzzApiClient().deletePost(postId);
    }
}