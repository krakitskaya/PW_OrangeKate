package com.kate.project.api.client;

import com.kate.project.api.client.dto.PostNewBuzzRequestDto;
import io.restassured.http.Method;

public class BuzzApiClient extends BaseApiClient {
    private static final String NEW_BUZZ_POST_CLIENT_URL = "/api/v2";

    public BuzzApiClient(String baseUri, String sessionCookie) {
        super(baseUri + NEW_BUZZ_POST_CLIENT_URL, sessionCookie);
    }

    public int createNewPost(String text, String type) {
        return sendRequest(Method.POST, "/buzz/posts", new PostNewBuzzRequestDto(text, type))
                .jsonPath()
                .getInt("data.post.id");
    }

    public void deletePost(int postId) {
        sendRequest(Method.DELETE, "/buzz/shares/" + postId, null);
    }
}