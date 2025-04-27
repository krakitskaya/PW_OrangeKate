package com.kate.project.api;

import org.junit.jupiter.api.Test;

import static com.kate.project.common.Users.ADMIN_USER;

public class Test4_DeleteNewPost_Negative_NotExistingPostId extends BaseApiTest {

    @Test
    public void deleteNonExistingPostApi() {
        ApiResponse<Void> apiResponse = defaultApiClientFactory
                .getBuzzApiClient()
                .deletePost(1000000000, ADMIN_USER);

        verifyError(apiResponse, 422, "Invalid Parameter");
    }
}