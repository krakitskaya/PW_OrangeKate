package com.kate.project.api;

import org.junit.jupiter.api.Test;

public class Test1_Api_AddNewPost extends BaseApiTest {

    @Test
    public void addNewPostApi() {
        apiClientFactory.getBuzzNewPostApiClient().createNewPost("NewPost", "text");
    }
}