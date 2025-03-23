package com.kate.project.api.client;

import com.kate.project.api.client.dto.PostNewBuzzRequestDto;
import io.restassured.http.Method;

public class BuzzApiClient extends BaseApiClient {

    public BuzzApiClient(String sessionCookie) {
        super(sessionCookie);
    }

    public int createNewPost(String text) {
        return sendRequest(Method.POST, "/buzz/posts", new PostNewBuzzRequestDto(text))
                .jsonPath()
                .getInt("data.post.id");
    }

    public void deletePost(int postId) {
        sendRequest(Method.DELETE, "/buzz/shares/" + postId, null);
    }
}