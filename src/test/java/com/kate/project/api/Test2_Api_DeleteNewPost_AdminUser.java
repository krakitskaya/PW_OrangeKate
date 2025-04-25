package com.kate.project.api;

import com.kate.project.common.helpers.BuzzHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.kate.project.common.Users.ADMIN_USER;

public class Test2_Api_DeleteNewPost_AdminUser extends BaseApiTest {
    private static final String postText = UUID.randomUUID().toString();

    @Test
    public void deleteNewPostApi() {
        int postId = BuzzHelper.createPostApi(postText);

        defaultApiClientFactory
                .getBuzzApiClient()
                .deletePostAndVerifySuccess(postId, ADMIN_USER);
    }
}
