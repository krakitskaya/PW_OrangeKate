package com.kate.project.api;

import com.kate.project.BasePlaywrightTest;
import org.junit.jupiter.api.Test;

public class Test1_Api_AddNewPost_AdminUser extends BasePlaywrightTest {

    @Test
    public void addNewPostApi() {
        defaultApiClientFactory.getBuzzApiClient().createNewPost("NewPost");
    }
}