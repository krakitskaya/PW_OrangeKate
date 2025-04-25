package com.kate.project.api;

import com.kate.project.common.helpers.BuzzHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.kate.project.common.Users.ADMIN_USER;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test1_Api_AddNewPost_AdminUser extends BaseApiTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void addNewPostApi() {
        BuzzHelper.createPostApi(postText);

        assertTrue(defaultApiClientFactory.getBuzzApiClient().getPosts(ADMIN_USER).stream()
                .anyMatch(post -> postText.equals(post.getText())));
    }
}