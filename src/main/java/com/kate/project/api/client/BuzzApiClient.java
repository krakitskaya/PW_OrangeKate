package com.kate.project.api.client;

import com.kate.project.api.client.dto.PostNewBuzzRequestDto;
import com.kate.project.web.entities.User;
import io.restassured.http.Method;

public class BuzzApiClient extends BaseApiClient {

    public BuzzApiClient(User user) {
        super(user);
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