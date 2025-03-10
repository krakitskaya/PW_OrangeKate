package com.kate.project.api;

import com.kate.project.helpers.ApiClientFactory;
import org.junit.jupiter.api.Test;

public class Test1_Api_CreateNewPost {
    private final ApiClientFactory apiClientFactory = new ApiClientFactory();

    @Test
    public void testPostEndpoint() {
        apiClientFactory.getBuzzNewPostApiClient().createNewPost("NewPost", "text");
    }
}