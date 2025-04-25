package com.kate.project.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.kate.project.common.Users.ADMIN_USER;

public class Test4_DeleteNewPost_Negative_NotExistingPostId extends BaseApiTest {

    @Test
    public void deleteNewPostApi() {

        Response response = defaultApiClientFactory
                .getBuzzApiClient()
                .deletePost(1000000000, ADMIN_USER);

        verifyCustomStatus(response, 422);
    }
}
