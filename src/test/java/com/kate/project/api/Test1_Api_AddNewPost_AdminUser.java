package com.kate.project.api;

import com.kate.project.helpers.BuzzHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test1_Api_AddNewPost_AdminUser extends BaseApiTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void addNewPostApi() {
        BuzzHelper.createPostApi(defaultApiClientFactory, postText);

        assertTrue(defaultApiClientFactory.getBuzzApiClient().getPosts().stream()
                .anyMatch(post -> postText.equals(post.getText())));
    }
}