package com.kate.project.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class Test5_DeleteNewPost_Negative_NotExistingPostId extends BaseApiTest {

    @Test
    public void deleteNewPostApi() {

        Response response = defaultApiClientFactory
                .getBuzzApiClient()
                .getDeletePostClient(1000000000)
                .send();

        verifyCustomStatus(response, 422);
    }
}
