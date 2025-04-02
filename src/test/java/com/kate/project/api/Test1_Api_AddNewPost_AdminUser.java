package com.kate.project.api;

import com.kate.project.BasePlaywrightTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class Test1_Api_AddNewPost_AdminUser extends BasePlaywrightTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void addNewPostApi() {
        defaultApiClientFactory.getBuzzApiClient().createNewPost(postText);
    }
}